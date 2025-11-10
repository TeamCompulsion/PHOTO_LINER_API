package kr.kro.photoliner.domain.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kr.kro.photoliner.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

}
