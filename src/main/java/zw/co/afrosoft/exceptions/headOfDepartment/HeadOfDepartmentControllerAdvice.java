package zw.co.afrosoft.exceptions.headOfDepartment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zw.co.afrosoft.exceptions.ApiExceptionResponse;
import zw.co.afrosoft.service.headOfDepartment.impl.HeadOfDepartmentServiceImpl;

import java.time.LocalDateTime;

@RestControllerAdvice(basePackageClasses = HeadOfDepartmentServiceImpl.class)

public class HeadOfDepartmentControllerAdvice {

	@ExceptionHandler(HeadOfDepartmentException.class)
	ResponseEntity<ApiExceptionResponse> handleHeadOfDepartmentException(HeadOfDepartmentException exception) {

		final ApiExceptionResponse response = new ApiExceptionResponse(exception.getErrorMessage(),
				HttpStatus.BAD_REQUEST, LocalDateTime.now());

		return ResponseEntity.status(response.getStatus()).body(response);
	}

}
