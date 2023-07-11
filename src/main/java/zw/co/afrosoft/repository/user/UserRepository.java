package zw.co.afrosoft.repository.user;

import zw.co.afrosoft.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

    Optional<User> findUserByEmployeeId(Long id);

    Optional<User> findUserByEmail(String email);


}
