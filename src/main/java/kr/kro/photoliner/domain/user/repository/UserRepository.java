package kr.kro.photoliner.domain.user.repository;

import java.util.Optional;
import kr.kro.photoliner.domain.user.model.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findUserById(Long userId);
}
