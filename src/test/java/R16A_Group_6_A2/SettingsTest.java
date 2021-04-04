package R16A_Group_6_A2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SettingsTest {
    @Test
    void testCreation(){
        Settings settings = new Settings();
        assertNotNull(settings);
    }

    @Test
    void testGetTimeout(){
        Settings settings = new Settings(false);
        assertNotNull(settings.getTimeout());
    }

    @Test
    void testDefault(){
        Settings settings = new Settings(false);
        assertEquals(settings.getTimeout(), 2.0);
    }

    @Test
    void testChangeTimeout(){
        Settings settings = new Settings(false);
        settings.setTimeout(7.2);
        assertEquals(settings.getTimeout(), 7.2);
    }

    @Test
    void testInvalidChangeTimeout(){
        Settings settings = new Settings(false);
        settings.setTimeout(-3.2);
        assertEquals(settings.getTimeout(), 2.0);
    }

    @Test
    void testPersistentData(){
        Settings settings = new Settings(false);
        settings.setTimeout(7.2);

        Settings loaded = new Settings(true);
        assertEquals(loaded.getTimeout(), 7.2);
        loaded.setTimeout(2.0);
    }


}
