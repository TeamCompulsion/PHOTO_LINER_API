package kr.kro.photoliner.domain.album.service;

import java.util.List;
import kr.kro.photoliner.domain.album.dto.AlbumPhotoItem;
import kr.kro.photoliner.domain.album.dto.AlbumPhotoItems;
import kr.kro.photoliner.domain.album.dto.request.AlbumCreateRequest;
import kr.kro.photoliner.domain.album.dto.request.AlbumDeleteRequest;
import kr.kro.photoliner.domain.album.dto.request.AlbumItemCreateRequest;
import kr.kro.photoliner.domain.album.dto.request.AlbumItemDeleteRequest;
import kr.kro.photoliner.domain.album.dto.request.AlbumPhotoMarkersRequest;
import kr.kro.photoliner.domain.album.dto.request.AlbumTitleUpdateRequest;
import kr.kro.photoliner.domain.album.dto.response.AlbumCreateResponse;
import kr.kro.photoliner.domain.album.dto.response.AlbumPhotoItemsResponse;
import kr.kro.photoliner.domain.album.dto.response.AlbumPhotoMarkersResponse;
import kr.kro.photoliner.domain.album.dto.response.AlbumsResponse;
import kr.kro.photoliner.domain.album.model.Album;
import kr.kro.photoliner.domain.album.repository.AlbumPhotoRepository;
import kr.kro.photoliner.domain.album.repository.AlbumRepository;
import kr.kro.photoliner.domain.user.model.User;
import kr.kro.photoliner.domain.user.repository.UserRepository;
import kr.kro.photoliner.global.code.ApiResponseCode;
import kr.kro.photoliner.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final AlbumPhotoRepository albumPhotoRepository;
    private final GeometryFactory geometryFactory;

    @Transactional
    public AlbumCreateResponse createAlbum(AlbumCreateRequest request) {
        User user = userRepository.findUserById(request.userId())
                .orElseThrow(() -> CustomException.of(ApiResponseCode.NOT_FOUND_USER, "user id: " + request.userId()));
        Album album = Album.builder()
                .title(request.title())
                .user(user)
                .build();
        Album savedAlbum = albumRepository.save(album);
        return AlbumCreateResponse.from(savedAlbum);
    }

    @Transactional(readOnly = true)
    public AlbumsResponse getAlbums(Long userId, Pageable pageable) {
        Page<Album> albums = albumRepository.findByUserId(userId, pageable);
        return AlbumsResponse.from(albums);
    }

    @Transactional(readOnly = true)
    public AlbumPhotoItemsResponse getAlbumPhotoItems(Long albumId) {
        List<AlbumPhotoItem> albumPhotoItems = albumPhotoRepository.findByAlbumId(albumId);
        return AlbumPhotoItemsResponse.from(albumPhotoItems);
    }

    @Transactional
    public void updateAlbumTitle(Long albumId, AlbumTitleUpdateRequest request) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ApiResponseCode.NOT_FOUND_ALBUM, "album id: " + albumId));
        album.updateTitle(request.title());
    }

    @Transactional
    public void deleteAlbums(AlbumDeleteRequest request) {
        albumRepository.deleteAllByIdInBatch(request.ids());
    }

    @Transactional
    public void createAlbumItems(Long albumId, AlbumItemCreateRequest request) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ApiResponseCode.NOT_FOUND_ALBUM, "album id: " + albumId));
        album.addPhotos(request.ids());
    }

    @Transactional
    public void deleteAlbumItems(Long albumId, AlbumItemDeleteRequest request) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ApiResponseCode.NOT_FOUND_ALBUM, "album id: " + albumId));
        album.removePhotos(request.ids());
    }

    @Transactional(readOnly = true)
    public AlbumPhotoMarkersResponse getAlbumPhotoMarkers(Long albumId, AlbumPhotoMarkersRequest request) {
        Point sw = geometryFactory.createPoint(request.getSouthWestCoordinate());
        Point ne = geometryFactory.createPoint(request.getNorthEastCoordinate());

        AlbumPhotoItems albumPhotoItems = albumPhotoRepository.getByAlbumIdInBox(albumId, sw, ne);

        return AlbumPhotoMarkersResponse.from(albumPhotoItems);
    }
}
