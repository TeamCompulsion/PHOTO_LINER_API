package kr.kro.photoliner.domain.photo.model;

import jakarta.persistence.*;
import kr.kro.photoliner.common.model.BaseEntity;
import kr.kro.photoliner.domain.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "photos")
@Getter
@Setter
public class Photo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NonNull
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @NonNull
    @Column(name = "captured_dt", nullable = false)
    private LocalDateTime capturedDt;

    @NonNull
    @Column(name = "location", nullable = false)
    private Point location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
