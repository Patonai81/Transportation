package hu.webuni.transportation;

import hu.webuni.transportation.test.AddressCRUDTest;
import hu.webuni.transportation.test.AddressSearchTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@Suite
@SuiteDisplayName("Teljes tesztkör")
@SelectClasses({AddressCRUDTest.class, AddressSearchTest.class})
//@IncludeTags({"address","as"})
public class TransportationApplicationTests {

}
