// Static demo: block every server action and switch language between the
// pre-rendered Spanish and English copies of the storefront.
(function () {
  'use strict';

  var meta = function (name) {
    var el = document.querySelector('meta[name="' + name + '"]');
    return el ? el.getAttribute('content') : null;
  };
  var lang = meta('demo-lang') || 'es';
  var message = lang === 'en'
    ? 'Static demo: this feature is not available.'
    : 'Demo estática: función no disponible.';

  var toastEl = null;
  var toastTimer = null;
  function toast() {
    if (!toastEl) {
      toastEl = document.createElement('div');
      toastEl.className = 'demo-toast';
      toastEl.setAttribute('role', 'status');
      toastEl.textContent = message;
      document.body.appendChild(toastEl);
    }
    toastEl.classList.add('is-visible');
    clearTimeout(toastTimer);
    toastTimer = setTimeout(function () { toastEl.classList.remove('is-visible'); }, 2500);
  }

  // Forms never reach a server: login, sign-up, add to cart, filters, checkout, cancel, avatar...
  document.addEventListener('submit', function (e) {
    e.preventDefault();
    e.stopPropagation();
    toast();
  }, true);

  // Action buttons and links that would change state on the server.
  var ACTIONS = 'a[href="#demo-action"], form button, form input[type="submit"], .btn-action, .search-btn';
  document.addEventListener('click', function (e) {
    var el = e.target.closest ? e.target.closest(ACTIONS) : null;
    if (!el || el.matches('[data-mobile-menu-open-btn], [data-mobile-menu-close-btn], [data-accordion-btn]')) return;
    e.preventDefault();
    e.stopPropagation();
    toast();
  }, true);

  // Language selector: jump to the pre-rendered page in the chosen language.
  document.addEventListener('DOMContentLoaded', function () {
    var selector = document.getElementById('language-selector');
    if (!selector) return;
    selector.addEventListener('change', function () {
      var target = meta('demo-alt-' + selector.value);
      if (target) {
        window.location.href = target;
      } else {
        selector.value = lang;
        toast();
      }
    });
  });
})();
