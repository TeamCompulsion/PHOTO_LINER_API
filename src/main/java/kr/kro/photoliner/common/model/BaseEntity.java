package kr.kro.photoliner.common.model;

import lombok.Getter;
import lombok.NonNull;

import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @NonNull
    @CreatedDate
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NonNull
    @CreatedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = false, updatable = true)
    private LocalDateTime updatedAt;
}
