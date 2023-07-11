package zw.co.afrosoft.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeadOfDepartmentResponse {

    private String firstname;
    private String lastname;
    private String department;
    private Long headOfDepartmentId;
}
