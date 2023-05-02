package app.appmeteo.model;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class ReadDataTest {

    ReadJSON jsonResponse = new ReadJSON("Marseille", "en", "metric",2);
    ReadData dataWeathers = new ReadData(jsonResponse.Current_REQUEST, jsonResponse.Daily_REQUEST);

    ReadDataTest() throws IOException {
    }

    @Test
    void getLon() {
        Object expectedLongitude = "5.5";
        Object actualLongitude = dataWeathers.getLon().toString();
        assertEquals(expectedLongitude,actualLongitude);
    }


    @Test
    void getId() {
        Object expected = 2995468;
        Object actual = dataWeathers.getId();
        assertEquals(expected,actual);
    }

    @Test
    void getName() {
        Object expected = "Arrondissement de Marseille";
        Object actual = dataWeathers.getName();
        assertEquals(expected,actual);

    }



}