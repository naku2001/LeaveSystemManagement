package zw.co.afrosoft.service.user;

import zw.co.afrosoft.exceptions.registration.RegistrationException;
import zw.co.afrosoft.repository.user.UserRepository;
import zw.co.afrosoft.security.dto.EmployeeRequest;
import zw.co.afrosoft.security.dto.RegistrationRequest;
import zw.co.afrosoft.utils.ExceptionMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {

	private static final String EMAIL_ALREADY_EXISTS = "Email Already Exists";

	private static final String USERNAME_ALREADY_EXISTS = "username Already Exists";

	private final UserRepository userRepository;

	private final ExceptionMessageAccessor exceptionMessageAccessor;

	public void validateUser(RegistrationRequest registrationRequest) {

		final String email = registrationRequest.getEmail();
		final String username = registrationRequest.getUsername();

		checkEmail(email);
		checkUsername(username);
	}

	private void checkUsername(String username) {

		final boolean existsByUsername = userRepository.existsByUsername(username);

		if (existsByUsername) {

			log.warn("{} is already being used!", username);

			final String existsUsername = USERNAME_ALREADY_EXISTS;
			throw new RegistrationException(existsUsername);
		}

	}

	private void checkEmail(String email) {

		final boolean existsByEmail = userRepository.existsByEmail(email);

		if (existsByEmail) {

			log.warn("{} is already being used!", email);

			final String existsEmail =  EMAIL_ALREADY_EXISTS;
			throw new RegistrationException(existsEmail);
		}
	}

	public void validateUser(EmployeeRequest request) {
		final String email = request.getEmail();
		final String username = request.getUsername();

		checkEmail(email);
		checkUsername(username);
	}
}
