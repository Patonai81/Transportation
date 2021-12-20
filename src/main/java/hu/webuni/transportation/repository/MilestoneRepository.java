package hu.webuni.transportation.repository;

import hu.webuni.transportation.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MilestoneRepository extends JpaRepository<Milestone,Long> {

    @Modifying
    @Query("update Milestone m set m.transportPlan.id = :transportId WHERE m.id = :milestoneId")
    void updateTransportId(@Param("milestoneId") Long id, @Param("transportId") Long transportId);
}
