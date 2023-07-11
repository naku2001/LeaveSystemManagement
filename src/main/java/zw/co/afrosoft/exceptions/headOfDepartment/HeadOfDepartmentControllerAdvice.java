package zw.co.afrosoft.exceptions.headOfDepartment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import zw.co.afrosoft.exceptions.ApiExceptionResponse;
import zw.co.afrosoft.exceptions.leave.LeaveException;
import zw.co.afrosoft.model.headOfDepartment.HeadOfDepartment;

import java.time.LocalDateTime;

@RestControllerAdvice(basePackageClasses = HeadOfDepartment.class)

public class HeadOfDepartmentControllerAdvice {

	@ExceptionHandler(LeaveException.class)
	ResponseEntity<ApiExceptionResponse> handleRegistrationException(LeaveException exception) {

		final ApiExceptionResponse response = new ApiExceptionResponse(exception.getErrorMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());

		return ResponseEntity.status(response.getStatus()).body(response);
	}

}
