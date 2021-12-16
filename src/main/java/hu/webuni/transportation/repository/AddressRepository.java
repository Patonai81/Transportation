package hu.webuni.transportation.repository;

import hu.webuni.transportation.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface AddressRepository extends JpaRepository<Address,Long> {

    boolean existsByIdEquals(@NonNull Long id);


}
