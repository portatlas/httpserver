package com.portatlas;

import com.portatlas.request.Request;
import com.portatlas.request.RequestMethod;
import com.portatlas.response.Response;
import com.portatlas.response.StatusCodes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerTest {


    private Server server;
    private Request getRootRequest = new Request(RequestMethod.GET, "/" , HttpVersion.CURRENT_VER);
    private Response OkResponse = Response.builder()
                                          .statusCode(StatusCodes.OK)
                                          .build();

    @Before
    public void setUp() {
        server = new Server();
        server.addRoutes();
    }

    @Test
    public void testConfigureServerReturnsServerSocketWithArgsGiven() throws IOException {
        String[] args = {"-p", "7070"};
        ServerSocket runningSocket = server.configureServer(args);

        assertEquals(7070, runningSocket.getLocalPort());
    }

    @Test
    public void testAddRootRequestAndResponse() {
        Response getRootResponse = OkResponse;

        assertEquals(getRootResponse.getStatus(), server.router.route(getRootRequest).getStatus());
    }

    @Test
    public void testAddHeadRequestAndResponse() {
        Request headRequest = new Request(RequestMethod.HEAD, "/" , HttpVersion.CURRENT_VER);
        Response headResponse = OkResponse;

        assertEquals(headResponse.getStatus(), server.router.route(headRequest).getStatus());
    }

    @Test
    public void testAddOptionsRequestAndResponse() {
        Request optionsRequest = new Request(RequestMethod.OPTIONS, "/method_options" , HttpVersion.CURRENT_VER);
        Response optionResponse = Response.builder()
                                          .statusCode(StatusCodes.OK)
                                          .header("Allow", "GET,HEAD,POST,OPTIONS,PUT" )
                                          .build();

        assertEquals(optionResponse.getStatus(), server.router.route(optionsRequest).getStatus());
        assertEquals(optionResponse.getHeader("Allow"), server.router.route(optionsRequest).getHeader("Allow"));
    }

    @Test
    public void testAddOptions2RequestAndResponse() {
        Request options2Request = new Request(RequestMethod.OPTIONS, "/method_options2" , HttpVersion.CURRENT_VER);
        Response optionResponse = Response.builder()
                                          .statusCode(StatusCodes.OK)
                                          .header("Allow", "GET,OPTIONS" )
                                          .build();

        assertEquals(optionResponse.getStatus(), server.router.route(options2Request).getStatus());
        assertEquals(optionResponse.getHeader("Allow"), server.router.route(options2Request).getHeader("Allow"));
    }

    @Test
    public void testAddPostRequestAndResponse() {
        Response postResponse = Response.builder()
                                        .statusCode(StatusCodes.OK)
                                        .build();
        Request postRequest = new Request(RequestMethod.POST, "/form" , HttpVersion.CURRENT_VER);

        assertEquals(postResponse.getStatus(), server.router.route(postRequest).getStatus());
    }

    @Test
    public void testAddPuttRequestAndResponse() {
        Response putResponse = Response.builder()
                                       .statusCode(StatusCodes.OK)
                                       .build();
        Request putRequest = new Request(RequestMethod.PUT, "/form" , HttpVersion.CURRENT_VER);

        assertEquals(putResponse.getStatus(), server.router.route(putRequest).getStatus());
    }

    @Test
    public void testGetRootReturnsResponseWithStatus200() {
        StringBuilder responseString = new StringBuilder();
        responseString.append("HTTP/1.1 200 OK\r\n");
        responseString.append("\r\n");

        assertEquals(responseString.toString(), server.handleRequest(getRootRequest));
    }

    @Test
    public void testHeadFoobarRequestReturnsResponseWithStatusNotFound() {
        Request getHeadFoobarRequest = new Request(RequestMethod.HEAD, "/foobar" , HttpVersion.CURRENT_VER);
        StringBuilder responseString = new StringBuilder();
        responseString.append("HTTP/1.1 404 Not Found\r\n");
        responseString.append("\r\n");

        assertEquals(responseString.toString(), server.handleRequest(getHeadFoobarRequest));
    }

}
