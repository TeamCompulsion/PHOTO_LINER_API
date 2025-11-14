package kr.kro.photoliner.domain.photo.repository;

import java.util.List;
import kr.kro.photoliner.domain.photo.model.Photo;
import kr.kro.photoliner.domain.photo.model.Photos;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface PhotoRepository extends Repository<Photo, Long> {

    List<Photo> findByUserId(
            Long userId,
            Pageable pageable
    );

    default Photos findPhotosByUserId(Long userId, Pageable pageable) {
        return new Photos(findByUserId(userId, pageable));
    }

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

    default Photos findPhotosByUserIdInBox(Long userId, Point sw, Point ne) {
        return new Photos(findByUserIdInBox(userId, sw, ne));
    }
}
