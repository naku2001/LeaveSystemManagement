package zw.co.afrosoft.service;

import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.model.RoleRequest;

public interface RoleService {
    ResponseEntity create(RoleRequest roleRequest);
}
