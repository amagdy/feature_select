package com.a1works.featureSelection;

import org.junit.Test;
import static org.junit.Assert.*;

public class ApplicationTest {

    @Test
    public void testIfApplicationRuns(){
        Application.main(new String[]{});
        Application app = Application.getInstance();
        assertEquals("Application is not running", true, app.isRunning());
    }
}
