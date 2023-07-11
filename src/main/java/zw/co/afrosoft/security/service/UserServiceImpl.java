package zw.co.afrosoft.security.service;

import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.model.user.User;
import zw.co.afrosoft.model.user.UserRole;
import zw.co.afrosoft.repository.employee.EmployeeRepository;
import zw.co.afrosoft.repository.user.UserRepository;
import zw.co.afrosoft.security.dto.AuthenticatedUserDto;
import zw.co.afrosoft.security.dto.RegistrationRequest;
import zw.co.afrosoft.security.dto.RegistrationResponse;
import zw.co.afrosoft.security.mapper.UserMapper;
import zw.co.afrosoft.service.UserValidationService;
import zw.co.afrosoft.utils.GeneralMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final EmployeeRepository employeeRepository;

	private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final UserValidationService userValidationService;

	private final GeneralMessageAccessor generalMessageAccessor;

	@Override
	public User findByUsername(String username) {

		return userRepository.findByUsername(username);
	}

	@Override
	public RegistrationResponse registration(RegistrationRequest registrationRequest) {

        userValidationService.validateUser(registrationRequest);


		final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setUserRole(UserRole.ADMIN);



		userRepository.save(user);


		final String username = registrationRequest.getUsername();

		final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, username);
        final String message    = "Registered user successfully";
		log.info("{} registered successfully!", username);

		return new RegistrationResponse(message);
	}

	@Override
	public AuthenticatedUserDto findAuthenticatedUserByUsername(String username) {

		final User user = findByUsername(username);

		return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
	}

	@Override
	public ResponseEntity findAll() {
		return ResponseEntity.ok().body(userRepository.findAll());
	}
}
