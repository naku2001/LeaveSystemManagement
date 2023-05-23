package zw.co.afrosoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.Role;

public interface RoleRepo  extends JpaRepository<Role,Long> {

}
