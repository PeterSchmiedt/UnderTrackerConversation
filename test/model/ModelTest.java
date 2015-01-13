package model;

import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class ModelTest extends WithApplication {

    @Before
    public void setUp() {
        fakeApplication(inMemoryDatabase());
    }

    @Test
    public void personTest() {
        
    }

}
