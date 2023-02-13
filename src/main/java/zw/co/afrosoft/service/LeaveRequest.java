package zw.co.afrosoft.service;

import lombok.*;
import zw.co.afrosoft.model.LeaveType;

import zw.co.afrosoft.model.Status;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveRequest {

    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private Long EmployeeId;
    private LeaveType leaveType;





}
