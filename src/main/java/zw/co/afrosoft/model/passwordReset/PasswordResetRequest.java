package zw.co.afrosoft.model.passwordReset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetRequest {

    private String code;
    private String newPassword;

}