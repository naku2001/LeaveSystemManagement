package zw.co.afrosoft.exceptions.headOfDepartment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class HeadOfDepartmentException extends RuntimeException {

	private final String errorMessage;

}
