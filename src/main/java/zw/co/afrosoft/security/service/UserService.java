package zw.co.afrosoft.security.service;

import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.model.user.User;
import zw.co.afrosoft.security.dto.AuthenticatedUserDto;
import zw.co.afrosoft.security.dto.RegistrationRequest;
import zw.co.afrosoft.security.dto.RegistrationResponse;

public interface UserService {

	User findByUsername(String username);

	RegistrationResponse registration(RegistrationRequest registrationRequest);

	AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

    ResponseEntity findAll();
}
