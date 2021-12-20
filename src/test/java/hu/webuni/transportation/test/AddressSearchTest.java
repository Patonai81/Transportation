package hu.webuni.transportation.test;


import hu.webuni.transportation.config.InitDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag("addressSearch")
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddressSearchTest {

    @Autowired
    WebTestClient webClient;

    @Autowired
    InitDb initDb;

    @BeforeEach
    public void initTest(){
        initDb.createTestData();
    }

    @Test
    public void testSearchWithEmptyAddresSearchDTO() {}

    @Test
    public void testSearchWith_CityLike_SortSizeMissing(){}

    @Test
    public void testSearchWith_CityLike_PageMissing(){}

    @Test
    public void testSearchWithCityLikePageable(){}

    @Test
    public void testSearchWithCityLikeANDCountry(){}





}
