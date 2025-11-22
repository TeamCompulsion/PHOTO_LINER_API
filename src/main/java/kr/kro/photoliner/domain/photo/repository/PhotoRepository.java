package kr.kro.photoliner.domain.photo.repository;

import java.util.Optional;
import kr.kro.photoliner.domain.photo.model.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Page<Photo> findByUserId(
            Long userId,
            Pageable pageable
    );

    Photo save(Photo photo);

    Optional<Photo> findById(Long photoId);

}
