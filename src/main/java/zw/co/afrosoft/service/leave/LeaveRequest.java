package zw.co.afrosoft.service.leave;

import lombok.*;
import zw.co.afrosoft.model.leave.LeaveType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveRequest {

    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private Long employeeId;
    private LeaveType leaveType;
    private String file;






}
