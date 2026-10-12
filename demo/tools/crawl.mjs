#!/usr/bin/env node
// Builds the static showcase in demo/ by crawling a locally running Electrosa:
// store.war and backoffice.war deployed on Tomcat (localhost:8080), the course
// database loaded, a demo customer ("demo") with a couple of orders and a
// non-empty cart, and a back office administrator ("admin").
//
// Usage (from the repository root, Node 18+, no dependencies):
//   node demo/tools/crawl.mjs
// Environment: ELECTROSA_HOST, DEMO_USER, DEMO_PASS, ADMIN_USER, ADMIN_PASS.

import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const HOST = process.env.ELECTROSA_HOST || 'http://localhost:8080';
const OUT = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const REPO = 'https://github.com/santiagodie/electrosa-fullstack-ecommerce';
const DEMO = { login_username: process.env.DEMO_USER || 'demo', login_password: process.env.DEMO_PASS || 'demo1234' };
const ADMIN = { login_username: process.env.ADMIN_USER || 'admin', login_password: process.env.ADMIN_PASS || 'admin' };

const ASSET_EXT = /\.(css|js|png|jpe?g|gif|svg|ico|webp|woff2?|ttf|eot|otf|webmanifest)$/i;
const ACTION_PATHS = [/^\/store\/cliente\/cesta\/vaciar$/, /^\/store\/auth\/logout$/, /^\/backoffice\/auth\/logout$/];

const TREES = {
  es: { app: '/store', dir: '', lang: 'es' },
  en: { app: '/store', dir: 'en/', lang: 'en' },
  bo: { app: '/backoffice', dir: 'backoffice/', lang: 'es' },
};

const TEXT = {
  es: {
    banner: 'Demo estática — la aplicación completa necesita Java, Tomcat y MySQL.',
    repo: 'Ver el código en GitHub',
    unavailableTitle: 'No disponible en la demo',
    unavailable: 'Esta página no forma parte de la demo estática. En la aplicación completa se genera en el servidor.',
    back: 'Volver',
    home: 'Ir al inicio',
  },
  en: {
    banner: 'Static demo — the full application needs Java, Tomcat and MySQL.',
    repo: 'View the source on GitHub',
    unavailableTitle: 'Not available in the demo',
    unavailable: 'This page is not part of the static demo. In the full application it is rendered by the server.',
    back: 'Go back',
    home: 'Go to the home page',
  },
};

// ---------------------------------------------------------------- HTTP session

class Session {
  constructor() { this.cookies = new Map(); }

  cookieHeader(pathname) {
    return [...this.cookies.values()]
      .filter(c => pathname.startsWith(c.path))
      .map(c => `${c.name}=${c.value}`)
      .join('; ');
  }

  remember(res) {
    for (const raw of res.headers.getSetCookie()) {
      const [pair, ...attrs] = raw.split(';');
      const eq = pair.indexOf('=');
      const name = pair.slice(0, eq).trim();
      const value = pair.slice(eq + 1).trim();
      const pathAttr = attrs.map(a => a.trim()).find(a => /^path=/i.test(a));
      const cookiePath = pathAttr ? pathAttr.slice(5) : '/';
      this.cookies.set(`${name}|${cookiePath}`, { name, value, path: cookiePath });
    }
  }

  async fetch(url, init = {}) {
    let target = new URL(url, HOST);
    for (let hop = 0; hop < 8; hop++) {
      const res = await fetch(target, {
        ...init,
        redirect: 'manual',
        headers: { ...(init.headers || {}), Cookie: this.cookieHeader(target.pathname) },
      });
      this.remember(res);
      const location = res.headers.get('location');
      if (res.status >= 300 && res.status < 400 && location) {
        target = new URL(location, target);
        init = {};
        continue;
      }
      return { res, url: target };
    }
    throw new Error(`Too many redirects: ${url}`);
  }

  async login(formPath, fields) {
    await this.fetch(formPath);
    const { url } = await this.fetch(formPath, {
      method: 'POST',
      body: new URLSearchParams({ next: '', ...fields }),
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    });
    if (url.pathname.endsWith('/auth/login')) throw new Error(`Login failed at ${formPath}`);
    return this;
  }
}

// ------------------------------------------------------------- URL ↔ file map

function normalize(u) {
  let pathname = u.pathname.replace(/;jsessionid=[^/?#]*/i, '');
  if (pathname.length > 1) pathname = pathname.replace(/\/$/, '');
  const params = new URLSearchParams(u.search);
  for (const key of [...params.keys()]) if (params.get(key) === '') params.delete(key);
  if (pathname === '/store/catalogo' && params.get('page') === '0') params.delete('page');
  if (pathname === '/backoffice/catalogue' && !params.has('p')) params.set('p', '1');
  const query = params.toString();
  return query ? `${pathname}?${query}` : pathname;
}

function pageFile(tree, key) {
  const { app, dir } = TREES[tree];
  const [pathname, query] = key.split('?');
  const local = pathname.slice(app.length) || '/';
  let name = local === '/' ? 'index' : local.replace(/^\//, '').replace(/\.jsp$/, '');
  if (query) name += '--' + query.replace(/[=&]/g, '-').replace(/[^A-Za-z0-9._-]/g, '');
  return `${dir}${name}.html`;
}

function assetFile(pathname) {
  if (pathname.startsWith('/store/')) return pathname.slice('/store/'.length);
  if (pathname.startsWith('/backoffice/')) return 'backoffice/' + pathname.slice('/backoffice/'.length);
  return 'misc' + pathname;
}

function rel(fromFile, toFile) {
  const r = path.posix.relative(path.posix.dirname(fromFile), toFile);
  return r || path.posix.basename(toFile);
}

function treeForPath(pathname, current) {
  if (pathname.startsWith('/backoffice')) return 'bo';
  return current === 'bo' ? 'es' : current;
}

// ------------------------------------------------------------------ crawl plan

const STORE_ANON = ['/store/', '/store/catalogo', '/store/catalogo?page=1', '/store/catalogo?page=2',
  '/store/blog', '/store/about/team', '/store/auth/login', '/store/auth/register', '/store/lang/prefs'];
const STORE_USER = ['/store/cliente/cesta', '/store/cliente/pedidos', '/store/cliente/pedidos/anulados',
  '/store/cliente/pedido/formulario', '/store/cliente/cuenta'];
const BO_ANON = ['/backoffice/auth/login'];
const BO_ADMIN = ['/backoffice/', '/backoffice/catalogue', '/backoffice/catalogue?p=2', '/backoffice/catalogue?p=3',
  '/backoffice/almacenes.jsp', '/backoffice/search.jsp'];

// Links followed from the seed pages (depth 1) and, for "deep" rules, from the
// pages found that way (depth 2: article detail -> article edit form).
const DISCOVER = [
  { re: /^\/store\/catalogo\/[A-Z][a-z]{2}-\d{3}[A-Z]{2}$/, who: 'anon' },
  { re: /^\/store\/blog\/[a-z0-9-]+$/, who: 'anon' },
  { re: /^\/store\/cliente\/pedidos\/[0-9a-f-]{36}$/, who: 'user' },
  { re: /^\/backoffice\/article\?artId=[A-Za-z0-9-]+&p=\d+$/, who: 'admin' },
  { re: /^\/backoffice\/article_mod\?artId=[A-Za-z0-9-]+&p=\d+$/, who: 'admin', deep: true },
];

const pages = { es: new Map(), en: new Map(), bo: new Map() }; // key -> { url, html }

function linksIn(html, base) {
  const out = [];
  for (const m of html.matchAll(/\shref=(["'])(.*?)\1/gi)) {
    try {
      const u = new URL(m[2].replace(/&amp;/g, '&'), base);
      if (u.origin === new URL(HOST).origin) out.push(normalize(u));
    } catch { /* ignore malformed */ }
  }
  return out;
}

async function capture(tree, session, key, mine) {
  if (pages[tree].has(key)) return;
  const { res, url } = await session.fetch(key);
  if (res.status !== 200 || !(res.headers.get('content-type') || '').includes('text/html')) {
    console.warn(`skip ${tree} ${key} -> ${res.status}`);
    return;
  }
  if (normalize(url) !== key) {
    console.warn(`skip ${tree} ${key} -> redirected to ${url.pathname}`);
    return;
  }
  const html = await res.text();
  pages[tree].set(key, { url: url.href, html });
  if (mine) mine.push(...linksIn(html, url));
}

async function crawlTree(tree, sessions, seeds) {
  const found = [];
  for (const [who, list] of Object.entries(seeds)) {
    for (const key of list) await capture(tree, sessions[who], normalize(new URL(key, HOST)), found);
  }
  const deeper = [];
  for (const key of new Set(found)) {
    const rule = DISCOVER.find(d => d.re.test(key));
    if (rule && sessions[rule.who]) await capture(tree, sessions[rule.who], key, deeper);
  }
  for (const key of new Set(deeper)) {
    const rule = DISCOVER.find(d => d.deep && d.re.test(key));
    if (rule && sessions[rule.who]) await capture(tree, sessions[rule.who], key, null);
  }
  console.log(`${tree}: ${pages[tree].size} pages`);
}

// ----------------------------------------------------------------- rewriting

const assets = new Map(); // pathname -> file

function mapRef(raw, baseUrl, tree, fromFile, isFormAction) {
  const value = raw.trim().replace(/&amp;/g, '&');
  if (!value || value.startsWith('#') || /^(javascript|mailto|tel|data):/i.test(value)) return raw;
  let u;
  try { u = new URL(value, baseUrl); } catch { return raw; }
  if (u.origin !== new URL(HOST).origin) return raw;
  const pathname = u.pathname.replace(/;jsessionid=[^/?#]*/i, '');

  if (ASSET_EXT.test(pathname)) {
    const file = assetFile(pathname);
    assets.set(pathname, file);
    return rel(fromFile, file);
  }
  const target = treeForPath(pathname, tree);
  const unavailable = rel(fromFile, `${TREES[target].dir}unavailable.html`);
  if (isFormAction) return unavailable;
  if (ACTION_PATHS.some(re => re.test(pathname))) return '#demo-action';
  const key = normalize(u);
  return pages[target].has(key) ? rel(fromFile, pageFile(target, key)) + u.hash : unavailable;
}

function rewriteCssUrls(text, baseUrl, tree, fromFile) {
  return text.replace(/url\((['"]?)([^'")]+)\1\)/g, (m, q, ref) => {
    if (/^(data:|https?:\/\/(?!localhost))/i.test(ref) || ref.startsWith('#')) return m;
    return `url(${q}${mapRef(ref, baseUrl, tree, fromFile, false)}${q})`;
  });
}

function chrome(tree, fromFile, altEs, altEn) {
  const lang = TREES[tree].lang;
  const t = TEXT[lang];
  const base = rel(fromFile, 'demo-assets/demo.css').replace(/demo\.css$/, '');
  const head = [
    `<meta name="demo-lang" content="${lang}">`,
    altEs ? `<meta name="demo-alt-es" content="${altEs}">` : '',
    altEn ? `<meta name="demo-alt-en" content="${altEn}">` : '',
    `<link rel="stylesheet" href="${base}demo.css">`,
  ].join('');
  const body = `<div class="demo-banner" role="note">${t.banner} <a href="${REPO}">${t.repo}</a></div>`
    + `<script src="${base}demo.js" defer></script>`;
  return { head, body };
}

function rewritePage(tree, key, { url, html }) {
  const file = pageFile(tree, key);
  let out = html
    .replace(/\s(href|src|action)=(["'])(.*?)\2/gi, (m, attr, q, val) =>
      ` ${attr}=${q}${mapRef(val, url, tree, file, attr.toLowerCase() === 'action')}${q}`)
    .replace(/\sonchange="this\.form\.submit\(\)"/g, '');
  out = rewriteCssUrls(out, url, tree, file);

  let altEs = null, altEn = null;
  if (tree !== 'bo') {
    altEs = rel(file, pages.es.has(key) ? pageFile('es', key) : 'index.html');
    altEn = rel(file, pages.en.has(key) ? pageFile('en', key) : 'en/index.html');
  }
  const { head, body } = chrome(tree, file, altEs, altEn);
  out = out.replace(/<\/head>/i, `${head}</head>`).replace(/<\/body>(?![\s\S]*<\/body>)/i, `${body}</body>`);
  write(file, out);
}

function unavailablePage(tree) {
  const lang = TREES[tree].lang;
  const t = TEXT[lang];
  const file = `${TREES[tree].dir}unavailable.html`;
  const home = rel(file, `${TREES[tree].dir}index.html`);
  const { head, body } = chrome(tree, file, null, null);
  write(file, `<!DOCTYPE html>
<html lang="${lang}">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Electrosa — ${t.unavailableTitle}</title>
${head}
</head>
<body class="demo-unavailable">
<main>
<h1>${t.unavailableTitle}</h1>
<p>${t.unavailable}</p>
<p><a href="javascript:history.back()">${t.back}</a> · <a href="${home}">${t.home}</a></p>
</main>
${body}
</body>
</html>
`);
}

function write(file, content) {
  const full = path.join(OUT, file);
  fs.mkdirSync(path.dirname(full), { recursive: true });
  fs.writeFileSync(full, content);
}

async function downloadAssets(session) {
  const done = new Set();
  while (done.size < assets.size) {
    for (const [pathname, file] of [...assets]) {
      if (done.has(pathname)) continue;
      done.add(pathname);
      const { res, url } = await session.fetch(pathname);
      if (res.status !== 200) { console.warn(`asset ${pathname} -> ${res.status}`); continue; }
      if (/\.css$/i.test(pathname)) {
        write(file, rewriteCssUrls(await res.text(), url.href, pathname.startsWith('/backoffice') ? 'bo' : 'es', file));
      } else {
        write(file, Buffer.from(await res.arrayBuffer()));
      }
    }
  }
  console.log(`assets: ${assets.size}`);
}

// ----------------------------------------------------------------------- main

const anonEs = new Session();
const anonEn = new Session(); await anonEn.fetch('/store/?lang=en');
const userEs = await new Session().login('/store/auth/login', DEMO);
const userEn = await new Session().login('/store/auth/login', DEMO); await userEn.fetch('/store/?lang=en');
const boAnon = new Session();
const admin = await new Session().login('/backoffice/auth/login', ADMIN);

await crawlTree('es', { anon: anonEs, user: userEs }, { anon: STORE_ANON, user: STORE_USER });
await crawlTree('en', { anon: anonEn, user: userEn }, { anon: STORE_ANON, user: STORE_USER });
await crawlTree('bo', { anon: boAnon, admin }, { anon: BO_ANON, admin: BO_ADMIN });

for (const tree of Object.keys(pages)) {
  for (const [key, page] of pages[tree]) rewritePage(tree, key, page);
  unavailablePage(tree);
}
await downloadAssets(anonEs);
fs.writeFileSync(path.join(OUT, '.nojekyll'), '');
