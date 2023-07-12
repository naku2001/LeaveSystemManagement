package zw.co.afrosoft.exceptions.department;

import lombok.Getter;

@Getter

public class DepartmentNotFoundException extends RuntimeException {
	public DepartmentNotFoundException(String message) {
		super(message);
	}
}