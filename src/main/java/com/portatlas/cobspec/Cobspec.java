package com.portatlas.cobspec;

import com.portatlas.handler.AuthHandler;
import com.portatlas.helpers.writers.EndpointWriter;
import com.portatlas.request.Request;
import com.portatlas.response.Response;
import com.portatlas.http_constants.StatusCodes;

import java.util.Arrays;
import java.util.List;

public class Cobspec {
    private static String statusCode;
    private static String body = "";
    private static EndpointWriter formWriter = new EndpointWriter();

    public static final List<String> requestTargets = Arrays.asList(CobspecResources.LOGS,
                                                                    CobspecResources.COOKIE,
                                                                    CobspecResources.EAT_COOKIE,
                                                                    CobspecResources.TEA,
                                                                    CobspecResources.COFFEE,
                                                                    CobspecResources.REDIRECT,
                                                                    CobspecResources.PARAMETERS,
                                                                    CobspecResources.FORM);

    public static Response processGetRequest(Request request) {
        switch (request.getRequestTarget()) {
            case CobspecResources.REDIRECT:
                return new RedirectResponse().run();
            case CobspecResources.COFFEE:
                return new TeapotResponse().run();
            case CobspecResources.TEA:
                statusCode = StatusCodes.OK;
                break;
            case CobspecResources.PARAMETERS:
                return new ParameterResponse(request).run();
            case CobspecResources.LOGS:
                return new AuthHandler(request).run();
            case CobspecResources.COOKIE:
                return new SetCookieResponse(request).run();
            case CobspecResources.EAT_COOKIE:
                return new UseCookieResponse(request).run();
            case CobspecResources.FORM:
                statusCode = StatusCodes.OK;
                body = formWriter.getMessage();
                break;
            default:
                statusCode = StatusCodes.NOT_FOUND;
        }
        Response response = Response.builder()
                                    .statusCode(statusCode)
                                    .body(body.getBytes())
                                    .build();

        return response;
    }

    public static Response processPostPutRequest(Request request) {
        switch (request.getRequestTarget()) {
            case CobspecResources.FORM:
                statusCode = StatusCodes.OK;
                formWriter.storeInput(request.getRequestBody());
                break;
        }
        Response response = Response.builder()
                                    .statusCode(statusCode)
                                    .body(body.getBytes())
                                    .build();

        return response;
    }

    public static Response processDeleteRequest(Request request) {
        switch (request.getRequestTarget()) {
            case CobspecResources.FORM:
                statusCode = StatusCodes.OK;
                formWriter.deleteStoredInfo();
                break;
        }
        Response response = Response.builder()
                                    .statusCode(statusCode)
                                    .body(body.getBytes())
                                    .build();

        return response;
    }
}
