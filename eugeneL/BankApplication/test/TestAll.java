/**
 * Package: com.acme.bankapp
 * Project: BankApplication
 */

import com.acme.bankapp.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AccountTest.class,
    ClientTest.class
})

public class TestAll {
}
