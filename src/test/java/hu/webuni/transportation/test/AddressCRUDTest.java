package hu.webuni.transportation.test;

import hu.webuni.transportation.config.InitDb;
import hu.webuni.transportation.dto.AddressDTO;
import hu.webuni.transportation.exception.base.ErrorData;
import hu.webuni.transportation.service.JwtService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static hu.webuni.transportation.config.RightConstants.ADDRESS_MANAGER;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("address")
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddressCRUDTest {

    @Autowired
    WebTestClient webClient;

    @Autowired
    InitDb initDb;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static String TOKEN;

    @BeforeAll
    public void createToken(){
        UserDetails user1 = User.builder()
                .username("TestAddress")
                .password("address")
                .authorities(ADDRESS_MANAGER.name())
                .passwordEncoder((password) -> passwordEncoder.encode(password))
                .build();

        TOKEN = jwtService.createJwtToken(user1);
    }

    @BeforeEach
    public void initTest(){
       initDb.createTestData();
    }

    @Test
    public void testCreateAddressPositive() {

        AddressDTO.AddressDTOBuilder testAddressBuilder = AddressDTO.builder("HU", "Kecskemét", "6000", "Liszt Ferenc", "15");
        testAddressBuilder.longitude(16.2);
        AddressDTO testAddress = testAddressBuilder.build();

        webClient.post()
                .uri("api/addresses")
                .headers(http -> http.setBearerAuth(TOKEN))
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
                .headers(http -> http.setBearerAuth(TOKEN))
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
                .headers(http -> http.setBearerAuth(TOKEN))
                .body(Mono.just(testAddress), AddressDTO.class)
                .exchange()
                .expectBody(List.class)
                .returnResult();

        List<ErrorData> errorData = (List<ErrorData>) result.getResponseBody();
        assertThat(errorData).extracting("errorCode").contains("BODY_ID_NOT_NULL");
        assertThat(errorData).asList().hasSize(1);
    }

    @Test
    public void testDeleteExistingAddress() {

        webClient.delete()
                .uri("api/addresses/1")
                .headers(http -> http.setBearerAuth(TOKEN))
                .exchange()
                .expectStatus()
                .isOk();

    }

    @Test
    public void testDeleteNonExistingAddress() {

        webClient.delete()
                .uri("api/addresses/100")
                .headers(http -> http.setBearerAuth(TOKEN))
                .exchange()
                .expectStatus()
                .isOk();

    }

    @Test
    public void testModifyAddressPositive() {

        AddressDTO.AddressDTOBuilder testAddressBuilder = AddressDTO.builder("HU", "city", "6000", "Liszt Ferenc", "15");
        AddressDTO testAddress = testAddressBuilder.build();
        AddressDTO addressFromRepo = getAllAddresses().get(0);

        EntityExchangeResult result = webClient.put()
                .uri("api/addresses/"+addressFromRepo.getId())
                .headers(http -> http.setBearerAuth(TOKEN))
                .body(Mono.just(testAddress), AddressDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AddressDTO.class)
                .returnResult();


        assertThat(result.getResponseBody()).isNotNull();
        assertThat(result.getResponseBody()).extracting("countryCode").isEqualTo("HU");
        assertThat(result.getResponseBody()).extracting("city").isEqualTo("city");

    }

    @Test
    public void testModifyAddressIDNegative() {

        AddressDTO.AddressDTOBuilder testAddressBuilder = AddressDTO.builder("HU", "city", "6000", "Liszt Ferenc", "15");
        testAddressBuilder.id(66l);
        AddressDTO testAddress = testAddressBuilder.build();
        AddressDTO addressFromRepo = getAllAddresses().get(0);

        EntityExchangeResult result = webClient.put()
                .uri("api/addresses/"+addressFromRepo.getId())
                .headers(http -> http.setBearerAuth(TOKEN))
                .body(Mono.just(testAddress), AddressDTO.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .returnResult();


        assertThat(result.getResponseBody()).isNotNull();
        assertThat(result.getResponseBody()).isEqualTo("incoming ID does not match");

    }

    @Test
    public void testModifyAddressBodyNULL() {

        AddressDTO testAddress = new AddressDTO();
        AddressDTO addressFromRepo = getAllAddresses().get(0);

        EntityExchangeResult <List <ErrorData>> result = webClient.put()
                .uri("api/addresses/"+addressFromRepo.getId())
                .headers(http -> http.setBearerAuth(TOKEN))
                .body(Mono.justOrEmpty(testAddress), AddressDTO.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBodyList(ErrorData.class)
                .returnResult();


        assertThat(result.getResponseBody()).isNotNull();
        assertThat(result.getResponseBody()).extracting("message").satisfies( message -> { message.stream().forEach( item -> { String message2 = (String) item;
        assertThat(message2).isEqualTo("Field value cannot be null");});});

    }

    public List<AddressDTO> getAllAddresses(){

        EntityExchangeResult<List <AddressDTO>> result = webClient.get()
                .uri("api/addresses")
                .headers(http -> http.setBearerAuth(TOKEN))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(AddressDTO.class)
                .returnResult();

        return (List<AddressDTO>) result.getResponseBody();

    }

}
