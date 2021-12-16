package hu.webuni.transportation.repository;

import hu.webuni.transportation.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section,Long> {
}
