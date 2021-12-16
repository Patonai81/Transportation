package hu.webuni.transportation.repository;

import hu.webuni.transportation.model.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportPlanRepository  extends JpaRepository<TransportPlan,Long>{

}
