package hu.webuni.transportation.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Builder(toBuilder = true, builderMethodName = "hiddenInternalBuilder")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AddressDTO {

    private Long id;
    @Size(min = 2, max = 2)
    private String countryCode;
    private String city;
    private String street;
    private String postalCode;
    private String houseNum;
    private double longitude;
    private double latitude;

    public static hu.webuni.transportation.dto.AddressDTO.AddressDTOBuilder builder(String countryCode, String city, String postalCode, String street,String houseNum) {
        return hiddenInternalBuilder().countryCode(countryCode).city(city).postalCode(postalCode).street(street).houseNum(houseNum);
    }

}

