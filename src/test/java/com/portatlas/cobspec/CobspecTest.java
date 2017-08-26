package com.portatlas.cobspec;

import com.portatlas.http_constants.HeaderName;
import com.portatlas.http_constants.HttpVersion;
import com.portatlas.helpers.Converter;
import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.http_constants.StatusCodes;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CobspecTest {
    @Test
    public void testRedirectRequestHasResponseWithStatus302WithHeaderLocationToRoot() {
        Request redirectRequest = new Request(RequestMethod.GET, CobspecResources.REDIRECT, HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.FOUND, Cobspec.processGetRequest(redirectRequest).getStatus());
        assertEquals("/", Cobspec.processGetRequest(redirectRequest).getHeader(HeaderName.LOCATION));
    }

    @Test
    public void testCoffeeRequestHasResponseWithStatus418AndBodyTeapot() {
        Request coffeeRequest = new Request(RequestMethod.GET, CobspecResources.COFFEE, HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.TEA_POT, Cobspec.processGetRequest(coffeeRequest).getStatus());
        assertEquals("I'm a teapot", Converter.bytesToString(Cobspec.processGetRequest(coffeeRequest).getBody()));
    }

    @Test
    public void testTeaRequestHasResponseWithStatus200() {
        Request teaRequest = new Request(RequestMethod.GET, CobspecResources.TEA, HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, Cobspec.processGetRequest(teaRequest).getStatus());
    }

    @Test
    public void testParameterRequestHasResponseWithStatus200AndBodyOfDecodedParams() {
        Request parameterRequest = new Request(RequestMethod.GET, CobspecResources.PARAMETERS, "variable_1 = Operators <, >", HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, Cobspec.processGetRequest(parameterRequest).getStatus());
        assertEquals("variable_1 = Operators <, >", Converter.bytesToString(Cobspec.processGetRequest(parameterRequest).getBody()));
    }

    @Test
    public void testUnauthRequestHasResponseWithStatus401HasHeadersBasicAuth() {
        Request unauthRequest = new Request(RequestMethod.GET, CobspecResources.LOGS, HttpVersion.CURRENT_VER);
        unauthRequest.addHeader(HeaderName.AUTH, "Basic 111taW46aHVudGVy2g==");

        assertEquals(StatusCodes.UNAUTHORIZED, Cobspec.processGetRequest(unauthRequest).getStatus());
        assertEquals("Basic", Cobspec.processGetRequest(unauthRequest).getHeader(HeaderName.WWW_AUTH));
        assertEquals("Request requires authentication", Converter.bytesToString(Cobspec.processGetRequest(unauthRequest).getBody()));
    }

    @Test
    public void testAuthRequestHasResponseWithStatus200HasHeadersBasicAuth() {
        Request authRequest = new Request(RequestMethod.GET, CobspecResources.LOGS, HttpVersion.CURRENT_VER);
        authRequest.addHeader(HeaderName.AUTH, "Basic YWRtaW46aHVudGVyMg==");

        assertEquals(StatusCodes.OK, Cobspec.processGetRequest(authRequest).getStatus());
        assertEquals("Basic", Cobspec.processGetRequest(authRequest).getHeader(HeaderName.WWW_AUTH));
    }

    @Test
    public void testSetCookieWithParamsHasResponseWithStatus200AndBodyEat() {
        Request setCookieRequest = new Request(RequestMethod.GET, CobspecResources.COOKIE, "chocolate", HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, Cobspec.processGetRequest(setCookieRequest).getStatus());
        assertEquals("Eat", Converter.bytesToString(Cobspec.processGetRequest(setCookieRequest).getBody()));
        assertEquals("chocolate", Cobspec.processGetRequest(setCookieRequest).getHeaders().get(HeaderName.SET_COOKIE));
    }

    @Test
    public void testDefaultHasResponseWithStatus404() {
        Request unknownRequest = new Request(RequestMethod.GET, "/foobar", HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.NOT_FOUND, Cobspec.processGetRequest(unknownRequest).getStatus());
    }

    @Test
    public void testPutRequestHasResponseWithStatus200AndStoresContentToForm() {
        Request getFormRequest = new Request(RequestMethod.GET, CobspecResources.FORM, HttpVersion.CURRENT_VER);
        Request putRequest = new Request(RequestMethod.PUT, CobspecResources.FORM, HttpVersion.CURRENT_VER);
        putRequest.setBody("data=fat");

        assertEquals(StatusCodes.OK, Cobspec.processPostPutRequest(putRequest).getStatus());
        assertEquals("data=fat", Converter.bytesToString(Cobspec.processGetRequest(getFormRequest).getBody()));
    }

    @Test
    public void testPostRequestHasResponseWithStatus200() {
        Request postRequest = new Request(RequestMethod.POST, CobspecResources.FORM, HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, Cobspec.processPostPutRequest(postRequest).getStatus());
    }

    @Test
    public void testDeleteRequestHasResponseWithStatus200() {
        Request putRequest = new Request(RequestMethod.PUT, CobspecResources.FORM, HttpVersion.CURRENT_VER);
        putRequest.setBody("data=fat");
        Request deleteRequest = new Request(RequestMethod.DELETE, CobspecResources.FORM, HttpVersion.CURRENT_VER);
        Request getFormRequest = new Request(RequestMethod.GET, CobspecResources.FORM, HttpVersion.CURRENT_VER);

        assertEquals(StatusCodes.OK, Cobspec.processPostPutRequest(deleteRequest).getStatus());
    }
}
