package hu.webuni.transportation.repository;

import hu.webuni.transportation.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

public interface AddressRepository extends JpaRepository<Address,Long> , JpaSpecificationExecutor<Address> {

    boolean existsByIdEquals(@NonNull Long id);


}
