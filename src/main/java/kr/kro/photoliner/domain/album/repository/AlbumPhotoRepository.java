package kr.kro.photoliner.domain.album.repository;

import java.util.List;
import kr.kro.photoliner.domain.album.model.view.AlbumPhotoView;
import kr.kro.photoliner.domain.album.model.view.AlbumPhotoViews;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumPhotoRepository extends JpaRepository<AlbumPhotoView, Long> {
    Page<AlbumPhotoView> findByAlbumId(Long albumId, Pageable pageable);

    @Query("""
            select apv
            from AlbumPhotoView apv
            where apv.userId = :userId
              and function('st_x', apv.location) between function('st_x', :sw) and function('st_x', :ne)
              and function('st_y', apv.location) between function('st_y', :sw) and function('st_y', :ne)
            order by apv.capturedDt desc
            """)
    List<AlbumPhotoView> findByUserIdInBox(Long userId, Point sw, Point ne);

    default AlbumPhotoViews getByUserIdInBox(Long userId, Point sw, Point ne) {
        return new AlbumPhotoViews(
                findByUserIdInBox(userId, sw, ne)
        );
    }
}
