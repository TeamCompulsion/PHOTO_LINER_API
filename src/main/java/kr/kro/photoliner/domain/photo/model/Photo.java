package kr.kro.photoliner.domain.photo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kr.kro.photoliner.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "photos")
@Getter
@Setter
public class Photo extends BaseEntity {

}
