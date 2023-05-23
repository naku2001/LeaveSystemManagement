package zw.co.afrosoft.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.GrantedAuthority;
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Authorities implements GrantedAuthority {

    ADMIN_USER("read/write"),
    EMPLOYEE_USER("read");
    private final String authority;
    Authorities(String authority)
    { this.authority = authority; }
    @Override
    public String getAuthority() {
        return authority;
    }
}
