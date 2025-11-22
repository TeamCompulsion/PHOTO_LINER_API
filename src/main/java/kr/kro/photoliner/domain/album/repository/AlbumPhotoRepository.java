package kr.kro.photoliner.domain.album.repository;

import java.util.List;
import kr.kro.photoliner.domain.album.dto.AlbumPhotoItem;
import kr.kro.photoliner.domain.album.dto.AlbumPhotoItems;
import kr.kro.photoliner.domain.album.model.PhotoItem;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumPhotoRepository extends JpaRepository<PhotoItem, Long> {
    @Query("""
                select new kr.kro.photoliner.domain.album.dto.AlbumPhotoItem(
                    pi.id,
                    pi.photoId,
                    p.fileName,
                    p.filePath,
                    p.thumbnailPath,
                    p.capturedDt,
                    p.location
                )
                from PhotoItem pi
                inner join Photo p on p.id = pi.photoId
                where pi.album.id = :albumId
            """)
    Page<AlbumPhotoItem> findByAlbumId(Long albumId, Pageable pageable);

    @Query("""
                select new kr.kro.photoliner.domain.album.dto.AlbumPhotoItem(
                            pi.id,
                            pi.photoId,
                            p.fileName,
                            p.filePath,
                            p.thumbnailPath,
                            p.capturedDt,
                            p.location
                        )
                        from PhotoItem pi
                        left outer join Photo p on p.id = pi.photoId
                        where pi.album.id = :albumId
                            and function('st_x', p.location) between function('st_x', :sw) and function('st_x', :ne)
                            and function('st_y', p.location) between function('st_y', :sw) and function('st_y', :ne)
                        order by p.capturedDt
            """)
    List<AlbumPhotoItem> findByAlbumIdInBox(Long albumId, Point sw, Point ne);

    default AlbumPhotoItems getByAlbumIdInBox(Long albumId, Point sw, Point ne) {
        return new AlbumPhotoItems(
                findByAlbumIdInBox(albumId, sw, ne)
        );
    }
}
