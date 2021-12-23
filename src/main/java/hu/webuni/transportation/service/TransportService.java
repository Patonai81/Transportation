package hu.webuni.transportation.service;

import hu.webuni.transportation.config.DelayConstantProperty;
import hu.webuni.transportation.dto.RegisterDelayDTO;
import hu.webuni.transportation.model.Address;
import hu.webuni.transportation.model.Milestone;
import hu.webuni.transportation.model.TransportPlan;
import hu.webuni.transportation.repository.AddressRepository;
import hu.webuni.transportation.repository.MilestoneRepository;
import hu.webuni.transportation.repository.TransportPlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;


@Slf4j
@Service
public class TransportService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TransportPlanRepository transportPlanRepository;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Autowired
    MilestoneService milestoneService;

    @Autowired
    DelayConstantProperty delayConstantProperty;


    public boolean checkifTranspotPlanExits(Long transportPlan) {
        return transportPlanRepository.findById(transportPlan).isPresent();
    }

    public boolean checkIfTransportPlanExistsWithGivenMilestone(Long milestoneId, Long transportPlanId) {
        return transportPlanRepository.getIfExistWithMilestoneId(milestoneId, transportPlanId) != null;
    }

    @Transactional
    public void updateTransportPlan(RegisterDelayDTO registerDelayDTO) {

        //get given milestone
        Milestone milestone = milestoneService.getMilestoneByTransportPlanAndMilestoneId(registerDelayDTO.getMilestoneId(), registerDelayDTO.getTransportPlanId());
        milestoneService.registerDelayOnMilestone(registerDelayDTO.getDelayInMinutes(), milestone);
        //check if other milestones needs to be updated
        Optional<Milestone> nextMilestone = milestoneService.getNextMilestoneIfExists(milestone);
        if (nextMilestone.isPresent()) {
            milestoneService.registerDelayOnMilestone(registerDelayDTO.getDelayInMinutes(), nextMilestone.get());
        }
        //update Profit
        updateProfit(registerDelayDTO.getTransportPlanId(), registerDelayDTO.getDelayInMinutes());
    }

    @Transactional
    public void updateProfit(Long transportPlanId, Long delayInMinutes) {
        Double discountPercentage = delayConstantProperty.getDelay().getLimits().floorEntry(delayInMinutes.intValue()).getValue();
        if (discountPercentage == 0) {
            log.debug("Delay is less than the smallest treshold, no discount!");
            return;
        }
        log.debug("Profit has to be discounbted with " + discountPercentage + "%");
        TransportPlan transportPlan = transportPlanRepository.findById(transportPlanId).get();
        double newProfit = ((100.0-discountPercentage)/100)*transportPlan.getProfit().doubleValue();
        log.debug("Original profit value is: "+transportPlan.getProfit());
        log.debug("Updated profit value is: "+newProfit);
        transportPlan.setProfit(BigDecimal.valueOf(newProfit));
    }


}
