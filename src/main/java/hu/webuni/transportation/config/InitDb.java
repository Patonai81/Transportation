package hu.webuni.transportation.config;

import hu.webuni.transportation.model.Address;
import hu.webuni.transportation.model.Milestone;
import hu.webuni.transportation.model.Section;
import hu.webuni.transportation.model.TransportPlan;
import hu.webuni.transportation.repository.AddressRepository;
import hu.webuni.transportation.repository.MilestoneRepository;
import hu.webuni.transportation.repository.SectionRepository;
import hu.webuni.transportation.repository.TransportPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class InitDb {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Autowired
    TransportPlanRepository transportPlanRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void deleteTestData(){
        transportPlanRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Transactional
    public void createTestData() {

        deleteTestData();

        Address address1 = Address.builder("HU", "Budapest", "1143", "Kamilla", "11").build();
        Address address2 = Address.builder("HR", "Zagreb", "5522", "Viola", "12").build();
        Address address3 = Address.builder("SK", "Pozsony", "2345", "EJF", "13").build();
        Address address4 = Address.builder("HU", "Budapest", "1163", "Rózsa", "18").build();
        Address address5 = Address.builder("HU", "Budapest", "1163", "Pipacs", "22/A").build();
        Address address6 = Address.builder("HU", "Budapest", "1113", "Hóvirág", "145/D").build();
        Address address7 = Address.builder("RO", "Bukarest", "1163231", "Hóvirág", "145/D").build();
        Address address8 = Address.builder("HU", "Bugyi", "4847", "Hóvirág", "145/D").build();
        addressRepository.saveAllAndFlush(Arrays.asList(address1, address2, address3, address4, address5, address6, address7, address8));

        Milestone milestone1 = Milestone.builder(address1).plannedTime(LocalDateTime.of(2021, 12, 24, 12, 0)).build();
        Milestone milestone2 = Milestone.builder(address2).plannedTime(LocalDateTime.of(2021, 12, 24, 14, 0)).build();
        Milestone milestone3 = Milestone.builder(address3).plannedTime(LocalDateTime.of(2021, 12, 24, 16, 0)).build();
        Milestone milestone4 = Milestone.builder(address4).plannedTime(LocalDateTime.of(2021, 12, 24, 18, 0)).build();
        Milestone milestone5 = Milestone.builder(address5).plannedTime(LocalDateTime.of(2021, 12, 24, 20, 0)).build();
        Milestone milestone6 = Milestone.builder(address6).plannedTime(LocalDateTime.of(2021, 12, 24, 22, 0)).build();


        milestoneRepository.saveAllAndFlush(Arrays.asList(milestone1, milestone2, milestone3, milestone4, milestone5, milestone6));

        Section section1 = Section.builder().fromMilestone(milestone1).toMilestone(milestone2).number(1).build();
        Section section2 = Section.builder().fromMilestone(milestone3).toMilestone(milestone4).number(2).build();
        Section section3 = Section.builder().fromMilestone(milestone5).toMilestone(milestone6).number(3).build();
        sectionRepository.saveAllAndFlush(Arrays.asList(section1, section2, section3));

        TransportPlan transportPlan1 = TransportPlan.builder().profit(BigDecimal.valueOf(300000l)).planSections(Arrays.asList(section1, section2, section3)).build();
        TransportPlan transportPlan2 = TransportPlan.builder().profit(BigDecimal.valueOf(300000l)).planSections(Arrays.asList(section2)).build();

        transportPlanRepository.saveAllAndFlush(Arrays.asList(transportPlan1, transportPlan2));


        updateSection(section1.getId(), transportPlan1.getId());
        updateSection(section2.getId(), transportPlan1.getId());
        updateSection(section3.getId(), transportPlan1.getId());

        updateMIlestone(milestone1.getId(), transportPlan1.getId());
        updateMIlestone(milestone2.getId(), transportPlan1.getId());
        updateMIlestone(milestone3.getId(), transportPlan1.getId());
        updateMIlestone(milestone4.getId(), transportPlan1.getId());
        updateMIlestone(milestone5.getId(), transportPlan1.getId());
        updateMIlestone(milestone6.getId(), transportPlan1.getId());


    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void updateSection(Long sectionId, Long transportPlanId) {
        sectionRepository.updateTransportId(sectionId, transportPlanId);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void updateMIlestone(Long milestoneId, Long transportPlanId) {
        milestoneRepository.updateTransportId(milestoneId, transportPlanId);
    }

}



