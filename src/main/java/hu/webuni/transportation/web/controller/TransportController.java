package hu.webuni.transportation.web.controller;

import hu.webuni.transportation.dto.RegisterDelayDTO;
import hu.webuni.transportation.dto.validator.RegisterDelayDTOValidator;
import hu.webuni.transportation.service.TransportService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/transportPlans")
@Component("transportController")
public class TransportController {

    @Autowired
    RegisterDelayDTOValidator registerDelayDTOValidator;

    @Autowired
    TransportService transportService;

    @GetMapping
    public void teszt(){
        transportService.updateTransportPlan();
    }

    @PostMapping("/{id}/delay")
    public void registerDelay(@RequestBody  RegisterDelayDTO registerDelayDTO, @PathVariable("id") Long id, BindingResult bindingResult) {
        log.debug("Register delay has been started to milestone: ");
        registerDelayDTO.setTransportPlanId(id);
        registerDelayDTOValidator.validate(registerDelayDTO,bindingResult);

    }

}
