package hu.webuni.transportation.repository;

import hu.webuni.transportation.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
