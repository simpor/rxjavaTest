package rxcircle;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelloWorldTest {

    @Test
    public void testGet() throws Exception {
        assertEquals("World", HelloWorld.get());
    }
}