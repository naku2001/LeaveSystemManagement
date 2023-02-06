package zw.co.afrosoft.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.afrosoft.model.LeaveType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveTypeRequest {


    private LeaveType leaveType;
    private int defaultDays;

}
