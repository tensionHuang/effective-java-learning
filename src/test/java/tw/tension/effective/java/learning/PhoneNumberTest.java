package tw.tension.effective.java.learning;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Rickey Huang on 2016/6/30.
 */
public class PhoneNumberTest {

    private static final Logger log = LoggerFactory.getLogger(PhoneNumberTest.class.getName());

    private PhoneNumber phoneNumber;

    @Before
    public void setUp() throws Exception {
        phoneNumber = new PhoneNumber(104, 50, 5566);
    }

    @Test
    public void toStringTest() throws Exception {
        log.info("toString: {}", phoneNumber);
    }

    @Test
    public void cloneTest() throws Exception {
        PhoneNumber copy = phoneNumber.clone();
        log.info("copy: {}", copy);
        boolean sameContent = copy.equals(phoneNumber);
        log.info("sameContent? {}", sameContent);
        log.info("copy: {}, origin: {}", copy.hashCode(), phoneNumber.hashCode());
        boolean sameRef = copy == phoneNumber;
        log.info("sameRef? {}", sameRef);

    }
}
