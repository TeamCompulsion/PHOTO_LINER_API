package kr.kro.photoliner.domain.album.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "albums_photos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PhotoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @JoinColumn(name = "photo_id", nullable = false)
    private Long photoId;

    public PhotoItem(Album album, Long photoId) {
        this.album = album;
        this.photoId = photoId;
    }

    public static PhotoItem of(Album album, Long photoId) {
        return new PhotoItem(album, photoId);
    }

    public void removeAlbum() {
        this.album = null;
    }
}
