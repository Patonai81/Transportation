package hu.webuni.transportation.service;

import hu.webuni.transportation.model.Address;
import hu.webuni.transportation.model.TransportPlan;
import hu.webuni.transportation.repository.AddressRepository;
import hu.webuni.transportation.repository.MilestoneRepository;
import hu.webuni.transportation.repository.TransportPlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public class TransportService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TransportPlanRepository transportPlanRepository;

    @Autowired
    MilestoneRepository milestoneRepository;

    public boolean checkifMilestoneExits(Long milestoneId){
        return milestoneRepository.findById(milestoneId).isPresent();
    }

    public boolean checkifTranspotPlanExits(Long transportPlan){
        return transportPlanRepository.findById(transportPlan).isPresent();
    }

    public boolean checkIfTransportPlanExists(Long milestoneId, Long transportPlanId){
        return transportPlanRepository.getIfExistWithMilestoneId(milestoneId,transportPlanId) != null;
    }

    public Address updateTransportPlan(){
   // TransportPlan t = transportPlanRepository.getIfAvailable(11l).get();
   //     System.out.println(t);
        return null;
    }




}
