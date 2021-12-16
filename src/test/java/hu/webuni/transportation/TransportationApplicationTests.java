package hu.webuni.transportation;

import hu.webuni.transportation.config.InitDb;
import hu.webuni.transportation.test.AddressCreationTest;
import hu.webuni.transportation.test.AddressFilterTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.springframework.beans.factory.annotation.Autowired;


@Suite
@SuiteDisplayName("Teljes tesztkör")
@SelectClasses({AddressCreationTest.class, AddressFilterTest.class})
//@IncludeTags({"address","as"})
public class TransportationApplicationTests {



}
