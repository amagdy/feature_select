package com.a1works.featureSelection;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public class ApplicationTest {

    @Test
    public void testIfApplicationRuns(){
        Application.main(new String[]{});
        Application app = Application.getInstance();
        assertEquals("Application is not running", true, app.isRunning());
    }
}
