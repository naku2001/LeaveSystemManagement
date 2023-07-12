package zw.co.afrosoft.exceptions.leave;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zw.co.afrosoft.controller.leave.LeaveRestController;
import zw.co.afrosoft.exceptions.ApiExceptionResponse;
import zw.co.afrosoft.exceptions.department.DepartmentNotFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice(basePackageClasses = LeaveRestController.class)

public class LeaveControllerAdvice {


	@ExceptionHandler(LeaveException.class)
	public ResponseEntity<Object> handleDepartmentNotFoundException(LeaveException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}

}
