package kr.kro.photoliner.domain.album.repository;

import kr.kro.photoliner.domain.album.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    Album save(Album album);

    Page<Album> findByUserId(Long userId, Pageable pageable);

}
