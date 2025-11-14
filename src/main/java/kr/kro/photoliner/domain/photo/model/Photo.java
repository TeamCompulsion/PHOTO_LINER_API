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
import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.kro.photoliner.common.model.BaseEntity;
import kr.kro.photoliner.domain.user.model.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

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

  public boolean isBetween(LocalDate start, LocalDate end) {
    LocalDate capturedDate = capturedDt.toLocalDate();
    return capturedDate.isAfter(start) && capturedDate.isBefore(end);
  }
}
