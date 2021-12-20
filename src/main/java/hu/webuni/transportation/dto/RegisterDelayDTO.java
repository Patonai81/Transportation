package hu.webuni.transportation.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDelayDTO {

    private Long transportPlanId;
    private Long milestoneId;
    private Long delayInMinutes;

}
