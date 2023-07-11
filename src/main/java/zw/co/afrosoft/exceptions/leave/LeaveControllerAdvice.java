package zw.co.afrosoft.exceptions.leave;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zw.co.afrosoft.controller.leave.LeaveRestController;
import zw.co.afrosoft.exceptions.ApiExceptionResponse;

import java.time.LocalDateTime;

@RestControllerAdvice(basePackageClasses = LeaveRestController.class)

public class LeaveControllerAdvice {

	@ExceptionHandler(LeaveException.class)
	ResponseEntity<ApiExceptionResponse> handleRegistrationException(LeaveException exception) {

		final ApiExceptionResponse response = new ApiExceptionResponse(exception.getErrorMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());

		return ResponseEntity.status(response.getStatus()).body(response);
	}

}
