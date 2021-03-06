package hu.webuni.transportation.repository;

import hu.webuni.transportation.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section,Long> {

    @Modifying
    @Query("update Section s set s.transportPlan.id = :transportId WHERE s.id = :sectionId")
    void updateTransportId(@Param("sectionId") Long id, @Param("transportId") Long transportId);

    @Query("select s from Section s where s.fromMilestone.id = :id or s.toMilestone.id = :id")
    Optional<Section> getSectionByMilestoneId(Long id);



}
