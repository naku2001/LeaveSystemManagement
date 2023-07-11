package zw.co.afrosoft.model.headOfDepartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.afrosoft.model.department.Departments;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HodRequest {
     private Departments departments;
}
