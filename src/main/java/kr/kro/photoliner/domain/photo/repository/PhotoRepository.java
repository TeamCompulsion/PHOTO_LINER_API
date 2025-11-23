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

    @Query("""
                select p
                from Photo p
                where p.userId = :userId
                    and (:hasLocation is null or (:hasLocation = true and p.location is not null) or (:hasLocation = false and p.location is null))
                    and (:hasCapturedDate is null or (:hasCapturedDate = true and p.capturedDt is not null) or (:hasCapturedDate = false and p.capturedDt is null))
            """)
    Page<Photo> findByUserIdWithFilters(
            Long userId,
            Boolean hasLocation,
            Boolean hasCapturedDate,
            Pageable pageable
    );

    @Query("""
                select p
                from Photo p
                left outer join PhotoItem pi on p.id = pi.photoId
                where p.userId = :userId
                    and function('st_x', p.location) between function('st_x', :sw) and function('st_x', :ne)
                    and function('st_y', p.location) between function('st_y', :sw) and function('st_y', :ne)
                order by p.capturedDt
            """)
    List<Photo> findByUserIdInBox(Long userId, Point sw, Point ne);

    default Photos getByUserIdInBox(Long userId, Point sw, Point ne) {
        return new Photos(
                findByUserIdInBox(userId, sw, ne)
        );
    }

    Photo save(Photo photo);

    Optional<Photo> findById(Long photoId);

}
