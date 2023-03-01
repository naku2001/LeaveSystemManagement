package zw.co.afrosoft.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import zw.co.afrosoft.model.User;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

	private String token;
	private User user;

}
