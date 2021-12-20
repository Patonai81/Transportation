package hu.webuni.transportation.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webuni.transportation.config.InitDb;
import hu.webuni.transportation.dto.AddressDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("addressFind")
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddressFindTest {

    @Autowired
    WebTestClient webClient;

    @Autowired
    InitDb initDb;

    @BeforeEach
    public void initTest(){
        initDb.createTestData();
    }

    @Test
    public void testFindAddressesPositive() {


        EntityExchangeResult<JsonNode> result = webClient.get()
                .uri("api/addresses")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(JsonNode.class)
                .returnResult();

        //Ha nem teszem bele a workaroundot nem működik az extracting rész, Classcast exceptiont dob
        ObjectMapper mapper = new ObjectMapper();
        List<AddressDTO> addresses = mapper.convertValue(
                result.getResponseBody(),
                new TypeReference<List<AddressDTO>>(){}
        );


       assertThat(result.getResponseBody()).hasSize(4);
       assertThat(addresses).extracting(AddressDTO::getCountryCode).usingDefaultElementComparator().contains("HU");

    }

    @Test
    public void testFindAddressPositive() {

        ArrayList<AddressDTO> addresses = (ArrayList<AddressDTO>) getAllAddresses();

        EntityExchangeResult<AddressDTO> result = webClient.get()
                .uri("api/addresses/"+addresses.get(0).getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AddressDTO.class)
                .value(item -> assertThat(item.getId()).isNotNull())
                .returnResult();

        assertThat(result.getResponseBody()).hasFieldOrProperty("countryCode");

    }

    @Test
    public void testFindAddressNegative() {


        EntityExchangeResult<String> result = webClient.get()
                .uri("api/addresses/100")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(String.class)
                .returnResult();

        assertThat(result.getResponseBody()).contains("Address cannot be found for given id");
    }

    public List<AddressDTO> getAllAddresses(){

        EntityExchangeResult<List <AddressDTO>> result = webClient.get()
                .uri("api/addresses")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(AddressDTO.class)
                .returnResult();

        return (List<AddressDTO>) result.getResponseBody();

    }




}
