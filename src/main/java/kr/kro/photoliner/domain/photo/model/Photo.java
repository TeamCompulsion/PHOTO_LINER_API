package kr.kro.photoliner.domain.photo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import kr.kro.photoliner.common.model.BaseEntity;
import kr.kro.photoliner.domain.album.model.Album;
import kr.kro.photoliner.domain.user.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "photos")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Photo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotNull
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @NotNull
    @Column(name = "thumbnail_path", nullable = false)
    private String thumbnailPath;

    @Column(name = "captured_dt")
    private LocalDateTime capturedDt;

    @Column(name = "location")
    private Point location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void updateCapturedDate(LocalDateTime capturedDt) {
        this.capturedDt = capturedDt;
    }

    public void updateLocation(Point location) {
        this.location = location;
    }

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

    public boolean isIncludedInAlbum(Album album) {
        return album.getItems().stream()
                .anyMatch(albumItem -> Objects.equals(albumItem.getPhotoId(), id));
    }
}
