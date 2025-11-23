package kr.kro.photoliner.domain.user.repository;

import java.util.Optional;
import kr.kro.photoliner.domain.user.model.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    boolean existsByEmail(String email);

    User save(User user);
    
    Optional<User> findUserById(Long userId);
}
