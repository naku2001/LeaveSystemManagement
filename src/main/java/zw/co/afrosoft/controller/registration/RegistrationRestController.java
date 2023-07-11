package zw.co.afrosoft.controller.registration;

import zw.co.afrosoft.security.dto.RegistrationRequest;
import zw.co.afrosoft.security.dto.RegistrationResponse;
import zw.co.afrosoft.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")

public class RegistrationRestController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<RegistrationResponse> registrationRequest(@Valid @RequestBody RegistrationRequest registrationRequest) {

		final RegistrationResponse registrationResponse = userService.registration(registrationRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
	}
    @GetMapping
    public ResponseEntity findAll(){
        return userService.findAll();
    }

}
