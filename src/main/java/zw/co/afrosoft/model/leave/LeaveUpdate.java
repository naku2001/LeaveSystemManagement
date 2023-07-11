package zw.co.afrosoft.model.leave;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveUpdate {
    @NotNull(message = "Please provide start date!")
    private LocalDate fromDate;
    @NotNull(message = "Please provide end date!")
    private LocalDate toDate;
    @NotEmpty(message = "Please provide a reason for the leave!")
    private String reason;
    private int duration;
    private LeaveType leaveType;
}
