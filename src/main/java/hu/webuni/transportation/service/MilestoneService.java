package hu.webuni.transportation.service;

import hu.webuni.transportation.exception.OrphanMilestoneException;
import hu.webuni.transportation.model.Milestone;
import hu.webuni.transportation.model.Ordinal;
import hu.webuni.transportation.model.Section;
import hu.webuni.transportation.repository.MilestoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class MilestoneService {

    @Autowired
    MilestoneRepository milestoneRepository;

    @Autowired
    SectionService sectionService;

    @Transactional

    public Milestone getMilestoneByTransportPlanAndMilestoneId(Long milestoneID, Long transportplanId) {
        return milestoneRepository.getMilestoneByTransportPlanIdAndMilestoneId(milestoneID, transportplanId).orElse(null);
    }

    @Transactional
    public boolean checkifMilestoneExits(Long milestoneId) {
        return milestoneRepository.findById(milestoneId).isPresent();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Milestone registerDelayOnMilestone(Long delayInMinutes, Milestone milestone) {
        LocalDateTime delayedTime = milestone.getPlannedTime().plusMinutes(delayInMinutes);
        milestone.setPlannedTime(delayedTime);
        return milestone;
    }

    @Transactional
    public Optional<Milestone> getNextMilestoneIfExists(Milestone sibling) {
        // Ő egy kezdőmileston egy sectionbe
        if (sibling.getOrdinal() == Ordinal.FROM) {
            log.debug("Getting parent Section for milestone" + sibling);
            Section parent = sectionService.getSectionByMilestoneId(sibling.getId());
            log.debug("Parent Section for milestone" + sibling);
            return Optional.of(parent.getToMilestone());
        } else if (sibling.getOrdinal() == Ordinal.TO) {
            log.debug("Getting NEXT sibling parent Section for milestone" + sibling);
            Optional<Section> opParent = sectionService.getNextSectionByMilestoneId(sibling.getId());
            log.debug("NEXT Parent Section for milestone" + opParent);
            //lehet, hogy ő volt az utolsó elem a listában
            if (opParent.isPresent()) {
                return Optional.of(opParent.get().getFromMilestone());
            } else {
                log.debug("Last Section of the transportPlan!");
                return Optional.ofNullable(null);
            }
        }
        return null;
    }

}
