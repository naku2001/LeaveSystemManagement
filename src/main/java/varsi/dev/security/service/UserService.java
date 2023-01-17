package varsi.dev.security.service;

import varsi.dev.model.User;
import varsi.dev.security.dto.AuthenticatedUserDto;
import varsi.dev.security.dto.RegistrationRequest;
import varsi.dev.security.dto.RegistrationResponse;

public interface UserService {

	User findByUsername(String username);

	RegistrationResponse registration(RegistrationRequest registrationRequest);

	AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

}
