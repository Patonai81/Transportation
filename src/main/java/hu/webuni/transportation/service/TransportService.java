package hu.webuni.transportation.service;

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



    public boolean checkifTranspotPlanExits(Long transportPlan){
        return transportPlanRepository.findById(transportPlan).isPresent();
    }

    public boolean checkIfTransportPlanExistsWithGivenMilestone(Long milestoneId, Long transportPlanId){
        return transportPlanRepository.getIfExistWithMilestoneId(milestoneId,transportPlanId) != null;
    }

    @Transactional
    public void updateTransportPlan(RegisterDelayDTO registerDelayDTO){

        //get given milestone
        Milestone milestone = milestoneService.getMilestoneByTransportPlanAndMilestoneId(registerDelayDTO.getMilestoneId(),registerDelayDTO.getTransportPlanId());
        milestoneService.registerDelayOnMilestone(registerDelayDTO.getDelayInMinutes(),milestone);
        //check if other milestones needs to be updated
        Optional <Milestone> nextMilestone = milestoneService.getNextMilestoneIfExists(milestone);
        if (nextMilestone.isPresent()){
            milestoneService.registerDelayOnMilestone(registerDelayDTO.getDelayInMinutes(),nextMilestone.get());
        }
    }




}
