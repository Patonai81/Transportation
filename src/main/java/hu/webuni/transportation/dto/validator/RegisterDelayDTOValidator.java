package hu.webuni.transportation.dto.validator;

import hu.webuni.transportation.dto.RegisterDelayDTO;
import hu.webuni.transportation.exception.RegisterDelayDataNotFoundException;
import hu.webuni.transportation.exception.RegisterDelayDataNotValidException;
import hu.webuni.transportation.exception.base.NotAppropriateRegistrationInput;
import hu.webuni.transportation.service.MilestoneService;
import hu.webuni.transportation.service.TransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class RegisterDelayDTOValidator implements Validator {

    @Autowired
    TransportService transportService;

    @Autowired
    MilestoneService milestoneService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterDelayDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target == null)
            throw new NotAppropriateRegistrationInput("Input Object cannot be Null!");

        RegisterDelayDTO request = (RegisterDelayDTO) target;
        if (request.getMilestoneId() == null || !milestoneService.checkifMilestoneExits((request).getMilestoneId())){
            throw new RegisterDelayDataNotFoundException("MIlestone with given Id does not exists");
        }
        if (request.getTransportPlanId() == null || !transportService.checkifTranspotPlanExits((request).getTransportPlanId())){
            throw new RegisterDelayDataNotFoundException("TransportPlan with given Id does not exists");
        }
        if (!transportService.checkIfTransportPlanExistsWithGivenMilestone(request.getMilestoneId(), request.getTransportPlanId())){
            throw new RegisterDelayDataNotValidException("TransportPlan with provided milestoneId does not exists");
        }

        log.debug("Valid Milestone ID, with existing transport plan!");

    }
}
