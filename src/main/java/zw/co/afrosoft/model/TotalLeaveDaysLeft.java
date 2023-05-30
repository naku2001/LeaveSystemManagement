package zw.co.afrosoft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class TotalLeaveDaysLeft {

    private int availableSickLeave;
    private int availableVacationLeave;
    private int availableUnpaidLeave;
}
