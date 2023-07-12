package zw.co.afrosoft.exceptions.leave;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class LeaveException extends RuntimeException {
	public LeaveException(String message) {
		super(message);
	}

}
