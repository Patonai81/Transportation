package hu.webuni.transportation.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.webuni.transportation.model.Address;
import hu.webuni.transportation.repository.specification.AddressSpecification;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Data
@ToString
public class AddressSearchDTO {

    private String countryCode;
    private String city;
    private String street;
    private String postalCode;

    @JsonIgnore
    @ToString.Exclude
    private Pageable pageable;

    public Specification<Address> toSpecification() {
        return new AddressSpecification(this);
    }

}
