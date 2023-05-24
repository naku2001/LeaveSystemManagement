package zw.co.afrosoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.PassWordResetCode;
import zw.co.afrosoft.model.User;

import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PassWordResetCode, Long> {

        Optional<PassWordResetCode> findByCode(String code);
        Optional<PassWordResetCode> findByUser(User user);


}
