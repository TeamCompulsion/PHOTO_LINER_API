package kr.kro.photoliner.domain.user.model;

import kr.kro.photoliner.common.model.BaseEntity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @CreatedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = false, updatable = true)
    private LocalDateTime updatedAt;
}
