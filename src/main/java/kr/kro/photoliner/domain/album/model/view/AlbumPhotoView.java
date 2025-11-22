package kr.kro.photoliner.domain.album.model.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.locationtech.jts.geom.Point;

@Entity
@Immutable
@Getter
@Table(name = "vw_album_photos")
public class AlbumPhotoView {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "photo_id", nullable = false)
    private Long photoId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "thumbnail_path")
    private String thumbnailPath;

    @Column(name = "captured_dt")
    private LocalDateTime capturedDt;

    @Column(name = "location")
    private Point location;

    @Column(name = "album_id")
    private Long albumId;

    @Column(name = "user_id")
    private Long userId;

    public Double getLatitude() {
        if (Objects.isNull(location)) {
            return null;
        }
        return location.getX();
    }

    public Double getLongitude() {
        if (Objects.isNull(location)) {
            return null;
        }
        return location.getY();
    }
}
