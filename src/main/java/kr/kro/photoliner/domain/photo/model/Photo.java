package kr.kro.photoliner.domain.photo.model;

import jakarta.persistence.*;
import kr.kro.photoliner.common.model.BaseEntity;
import kr.kro.photoliner.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(name = "photos")
@Getter
@Setter
public class Photo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "captured_dt", nullable = false)
    private LocalDateTime capturedDt;

    @Column(name = "location", nullable = false)
    private Point location;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
