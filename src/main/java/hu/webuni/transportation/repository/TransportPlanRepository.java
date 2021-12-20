package hu.webuni.transportation.repository;

import hu.webuni.transportation.model.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransportPlanRepository  extends JpaRepository<TransportPlan,Long>{

    @Query("select t from TransportPlan t join Milestone m on m.transportPlan.id = t.id where m.id = :milestoneid and t.id= :transportPlanId")
    public TransportPlan getIfExistWithMilestoneId(Long milestoneid, Long transportPlanId);



}
