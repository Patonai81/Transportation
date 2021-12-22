package hu.webuni.transportation.service;

import hu.webuni.transportation.exception.OrphanMilestoneException;
import hu.webuni.transportation.model.Section;
import hu.webuni.transportation.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    SectionRepository sectionRepository;

    public Section getSectionByMilestoneId(Long milestoneId) {
        return sectionRepository.getSectionByMilestoneId(milestoneId).orElseThrow(() -> new OrphanMilestoneException("Milestone does not have section parent"));
    }

    public Optional<Section> getNextSectionByMilestoneId(Long milestoneId) {
        Section section = getSectionByMilestoneId(milestoneId);
        return sectionRepository.findById((section.getId() + 1));
    }


}
