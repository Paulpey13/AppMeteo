package app.appmeteo.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorsTest {

    private Authors authors = new Authors(Arrays.asList("Martin Dupont", "Marie Martin", "François Cordonnier"));

    public AuthorsTest() throws IOException {
    }

    @Test
    public void testToString() {
        String expected = "Martin Dupont, Marie Martin, François Cordonnier";
        assertEquals(expected, authors.toString());
    }



}

