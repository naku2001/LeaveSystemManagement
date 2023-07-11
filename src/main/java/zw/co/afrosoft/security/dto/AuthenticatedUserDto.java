package zw.co.afrosoft.security.dto;

import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.user.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedUserDto {

	private String firstName;
	private String lastName;
	private Employee employee;
	private String email;
	private Long id;

	private String username;

	private String password;

	private UserRole userRole;

}
