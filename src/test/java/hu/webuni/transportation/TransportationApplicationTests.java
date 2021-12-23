package hu.webuni.transportation;

import hu.webuni.transportation.test.AddressCRUDTest;
import hu.webuni.transportation.test.AddressFindTest;
import hu.webuni.transportation.test.AddressSearchTest;
import hu.webuni.transportation.test.TransportPlanDelayTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@Suite
@SuiteDisplayName("Teljes tesztkör")
@SelectClasses({AddressCRUDTest.class, AddressFindTest.class, AddressSearchTest.class, TransportPlanDelayTest.class})
//@IncludeTags({"address","as"})
public class TransportationApplicationTests {

}
