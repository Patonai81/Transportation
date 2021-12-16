package hu.webuni.transportation;

import hu.webuni.transportation.dto.AddressDTO;
import hu.webuni.transportation.exception.base.ErrorData;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Streams.stream;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressTest {

    @Autowired
    WebTestClient webClient;

    @Test
    public void testCreateAddressPositive() {

        AddressDTO.AddressDTOBuilder testAddressBuilder = AddressDTO.builder("HU", "Kecskemét", "6000", "Liszt Ferenc", "15");
        testAddressBuilder.longitude(16.2);
        AddressDTO testAddress = testAddressBuilder.build();

        webClient.post()
                .uri("api/addresses")
                .body(Mono.just(testAddress), AddressDTO.class)
                .exchange()
                .expectStatus()
                .isOk();

    }

    @Test
    public void testCreateAddressNegative() {

        AddressDTO.AddressDTOBuilder testAddressBuilder = AddressDTO.builder("HU", null, "6000", "Liszt Ferenc", "15");
        testAddressBuilder.longitude(16.2);
        AddressDTO testAddress = testAddressBuilder.build();

        webClient.post()
                .uri("api/addresses")
                .body(Mono.just(testAddress), AddressDTO.class)
                .exchange()
                .expectStatus()
                .isBadRequest();

    }

    @Test
    public void testCreateAddressIDSetNegative() {

        AddressDTO.AddressDTOBuilder testAddressBuilder = AddressDTO.builder("HU", "city", "6000", "Liszt Ferenc", "15");
        testAddressBuilder.id(2l);
        AddressDTO testAddress = testAddressBuilder.build();

        EntityExchangeResult result = webClient.post()
                .uri("api/addresses")
                .body(Mono.just(testAddress), AddressDTO.class)
                .exchange()
                .expectBody(List.class)
                .returnResult();

        List<ErrorData> errorData = (List<ErrorData>) result.getResponseBody();
        assertThat(errorData).extracting("errorCode").contains("BODY_ID_NOT_NULL");
        assertThat(errorData).asList().hasSize(1);
    }

}
