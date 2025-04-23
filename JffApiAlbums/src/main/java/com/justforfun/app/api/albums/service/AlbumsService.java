package com.justforfun.app.api.albums.service;

import com.justforfun.app.api.albums.data.AlbumEntity;

import java.util.List;

public interface AlbumsService {
    List<AlbumEntity> getAlbums(String userId);
}
