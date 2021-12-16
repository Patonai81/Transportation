package hu.webuni.transportation.repository;

import hu.webuni.transportation.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone,Long> {
}
