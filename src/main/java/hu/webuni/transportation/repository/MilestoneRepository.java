package hu.webuni.transportation.repository;

import hu.webuni.transportation.model.Milestone;
import hu.webuni.transportation.model.Ordinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MilestoneRepository extends JpaRepository<Milestone,Long> {

    @Modifying
    @Query("update Milestone m set m.transportPlan.id = :transportId WHERE m.id = :milestoneId")
    void updateTransportId(@Param("milestoneId") Long id, @Param("transportId") Long transportId);


    @Query("select m from Milestone m  where m.transportPlan.id = :transportId and m.id = :milestoneId")
    Optional<Milestone> getMilestoneByTransportPlanIdAndMilestoneId(@Param("milestoneId") Long id, @Param("transportId") Long transportId);

    @Query("select m from Milestone m where m.transportPlan is null")
    List<Milestone> getOrphanMilestones();

    @Query("select m from Milestone m where m.ordinal = :ordinal ")
    List<Milestone> getOrdinalMilestones(Ordinal ordinal);
}
