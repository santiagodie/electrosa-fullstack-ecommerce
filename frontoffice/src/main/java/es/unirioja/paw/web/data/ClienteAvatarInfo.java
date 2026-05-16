/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web.data;

/**
 *
 * @author Santiago Die
 */

import es.unirioja.paw.jpa.ClienteEntity;

public class ClienteAvatarInfo {

    private String gravatarHashId;
    private String clienteImage;
    private ImageSource imageSource;

    private enum ImageSource {
        Default,
        Gravatar,
        LocalImage
    }

    public ClienteAvatarInfo(ClienteEntity cliente) {
        imageSource = ImageSource.Default;
        this.gravatarHashId = cliente.getGravatarHashId();
        this.clienteImage = cliente.getImagenAvatar();
        if (gravatarHashId != null && !gravatarHashId.isBlank()) {
            imageSource = ImageSource.Gravatar;
        }
        // La imagen subida tiene preferencia sobre Gravatar
        if (clienteImage != null && !clienteImage.isBlank()) {
            imageSource = ImageSource.LocalImage;
        }

    }

    public boolean isDefault() {
        if (imageSource == null) {
            return true;
        }
        if (imageSource == ImageSource.Default) {
            return true;
        }
        return false;
    }

    public boolean isRemoteURL() {
        if (imageSource == null) {
            return false;
        }
        switch (imageSource) {
            case Gravatar:
                return true;
        }
        return false;
    }

    public String getImageSource() {
        if (imageSource == null) {
            return null;
        }
        switch (imageSource) {
            case LocalImage:
                return clienteImage;
            case Gravatar:
                return String.format("https://gravatar.com/avatar/%s", gravatarHashId);
        }

        return null;
    }
}