package hu.webuni.transportation.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Builder(toBuilder = true, builderMethodName = "hiddenInternalBuilder")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 2)
    @NonNull
    private String countryCode;
    @NonNull
    private String city;
    @NonNull
    private String street;
    @NonNull
    private String postalCode;
    @NonNull
    private String houseNum;
    private double longitude;
    private double latitude;


    public static AddressBuilder builder(String countryCode,String city,String postalCode,String street,String houseNum) {
        return hiddenInternalBuilder().countryCode(countryCode).city(city).postalCode(postalCode).street(street).houseNum(houseNum);
    }

    public Address(Address address){
        this.countryCode=address.getCountryCode();
        this.city= address.getCity();
        this.houseNum=address.getHouseNum();
        this.latitude=address.getLatitude();
        this.postalCode= address.getPostalCode();
    }


}
