package kr.kro.photoliner.domain.photo.repository;

import java.util.List;
import java.util.Optional;
import kr.kro.photoliner.domain.photo.model.Photo;
import kr.kro.photoliner.domain.photo.model.Photos;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Page<Photo> findByUserId(
            Long userId,
            Pageable pageable
    );

    List<Photo> findByIdIn(List<Long> ids);

    @Query("""
            select p
            from Photo p
            where p.user.id = :userId
              and function('st_x', p.location) between function('st_x', :sw) and function('st_x', :ne)
              and function('st_y', p.location) between function('st_y', :sw) and function('st_y', :ne)
            order by p.capturedDt desc
            """)
    List<Photo> findByUserIdInBox(
            Long userId,
            Point sw,
            Point ne
    );

    default Photos getPhotosByUserIdInBox(Long userId, Point sw, Point ne) {
        return new Photos(findByUserIdInBox(userId, sw, ne));
    }

    Photo save(Photo photo);

    Optional<Photo> findById(Long photoId);


}
