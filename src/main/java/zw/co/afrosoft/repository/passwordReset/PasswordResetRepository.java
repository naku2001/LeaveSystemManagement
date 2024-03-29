package zw.co.afrosoft.repository.passwordReset;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import zw.co.afrosoft.model.passwordReset.PassWordResetCode;

import java.util.List;
import java.util.Optional;
@Repository

public interface PasswordResetRepository extends JpaRepository<PassWordResetCode, Long> {
    Optional<PassWordResetCode> findPassWordResetCodeByCode(String code);

    Optional<PassWordResetCode> findByCode(String code);
    List<PassWordResetCode> findAllByCode(String code);
}
