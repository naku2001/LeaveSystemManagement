package zw.co.afrosoft.repository;

import zw.co.afrosoft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

    Optional<User> findUserByEmployeeId(Long id);

    Optional<User> findByEmail(String email);
}
