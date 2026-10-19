package cucumber.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.DriverManager;

public class HooksUI {

    @Before
    public void setUp() {
        DriverManager.getDriver();
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
