package kr.kro.photoliner.domain.photo.repository;

import java.util.List;
import kr.kro.photoliner.domain.photo.model.AlbumPhoto;
import kr.kro.photoliner.domain.photo.model.AlbumPhotos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumPhotoRepository extends JpaRepository<AlbumPhoto, Long> {

    List<AlbumPhoto> findByPhotoIdIn(List<Long> ids);

    default AlbumPhotos getByPhotoIdIn(List<Long> ids) {
        return new AlbumPhotos(
                findByPhotoIdIn(ids)
        );
    }
}
