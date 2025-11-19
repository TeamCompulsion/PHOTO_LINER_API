package kr.kro.photoliner.domain.album.service;

import kr.kro.photoliner.domain.album.dto.request.AlbumCreateRequest;
import kr.kro.photoliner.domain.album.dto.request.AlbumDeleteRequest;
import kr.kro.photoliner.domain.album.dto.response.AlbumCreateResponse;
import kr.kro.photoliner.domain.album.dto.response.AlbumsResponse;
import kr.kro.photoliner.domain.album.model.Album;
import kr.kro.photoliner.domain.album.repository.AlbumRepository;
import kr.kro.photoliner.domain.user.model.User;
import kr.kro.photoliner.domain.user.repository.UserRepository;
import kr.kro.photoliner.global.code.ApiResponseCode;
import kr.kro.photoliner.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    @Transactional
    public AlbumCreateResponse createAlbum(AlbumCreateRequest request) {
        User user = userRepository.findUserById(request.userId())
                .orElseThrow(() -> CustomException.of(ApiResponseCode.NOT_FOUND_USER, "user id: " + request.userId()));
        Album album = Album.builder()
                .name(request.name())
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

    @Transactional
    public void deleteAlbums(AlbumDeleteRequest request) {
        albumRepository.deleteAllByIdInBatch(request.ids());
    }
}
