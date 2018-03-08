package com.portatlas.helpers.http_helpers;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.rules.ExpectedException;

public class AuthenticationTest {
    private String authorizationValue = "Basic YWRtaW46aHVudGVyMg==";
    private String badFormatAuthorizationValue = "YWRtaW46aHVudGVy==";
    private String invalidAuthorizationValue = "Basic 111taW46aHVudGVy2g==";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testIsValidFormat() {
        assertTrue(Authentication.isValidFormat(authorizationValue));
    }

    @Test
    public void testGetCredentials() throws Exception {
        assertEquals("YWRtaW46aHVudGVyMg==", Authentication.getCredentials(authorizationValue));
    }

    @Test
    public void testGetCredentialsThrowsErrorIfFormatIsWrong() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Invalid Auth Format");
        Authentication.getCredentials(badFormatAuthorizationValue);
    }

    @Test
    public void testDecodeCredentials() {
        assertEquals("admin:hunter2", Authentication.decodeCredentials("YWRtaW46aHVudGVyMg=="));
    }

    @Test
    public void testIsAuthenticated() throws Exception {
        assertTrue(Authentication.isValidCredentials(authorizationValue));
    }

    @Test
    public void testIsNotAuthenticated() throws Exception {
        assertFalse(Authentication.isValidCredentials(invalidAuthorizationValue));
    }
}
