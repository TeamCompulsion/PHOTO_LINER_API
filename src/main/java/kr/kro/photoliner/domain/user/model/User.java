package kr.kro.photoliner.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.kro.photoliner.common.model.BaseEntity;
import kr.kro.photoliner.global.kakao.login.dto.response.KakaoProfileResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    public User(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public static User from(KakaoProfileResponse profileResponse) {
        return new User(
                profileResponse.id().toString(),
                profileResponse.kakaoAccount().profile().nickname(),
                profileResponse.kakaoAccount().email()
        );
    }
}
