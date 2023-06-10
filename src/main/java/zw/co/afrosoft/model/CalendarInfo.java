package zw.co.afrosoft.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarInfo {

    private String start;
    private String end;
    private String title;
    private String leaveType;
}
