package com.portatlas.helpers.writers;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EndpointWriterTest {
    private EndpointWriter logWriter;
    private EndpointWriter formWriter;

    @Before
    public void setUp() {
        logWriter = new EndpointWriter();
        formWriter = new EndpointWriter();
    }

    @Test
    public void testAppendsStringsToMessage() {
        String firstLog = "hello";
        String secondLog = "world";

        logWriter.appendToMessage(firstLog);
        logWriter.appendToMessage(secondLog);

        assertEquals(firstLog + "\n" + secondLog + "\n", logWriter.getMessage());
    }

    @Test
    public void testWritesInputToStoredInfo() {
        String formInput = "input";

        formWriter.storeInput(formInput);

        assertEquals(formInput, formWriter.getMessage());
    }

    @Test
    public void testDeletesInputFromStoredInfo() {
        String formInput = "input";

        formWriter.storeInput(formInput);
        formWriter.deleteStoredInfo();

        assertEquals("", formWriter.getMessage());
    }
}
