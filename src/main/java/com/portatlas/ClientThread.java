package com.portatlas;

import com.portatlas.helpers.Converter;
import com.portatlas.request.Request;
import com.portatlas.request.RequestParser;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread {
    private Socket socket;
    private Directory directory;
    private Router router;

    public ClientThread(Socket socket, Router router, Directory directory) {
        this.socket = socket;
        this.directory = directory;
        this.router = router;
    }

    public void run() {
        try {
            InputStream requestInputStream = socket.getInputStream();
            Request request = buildRequestFromInputStream(requestInputStream);
            byte[] httpResponse = Controller.handleRequest(request, router, directory);
            OutputStream outPutStream = socket.getOutputStream();
            returnResponseToOutputStream(httpResponse, outPutStream);
            System.out.println(Converter.bytesToString(httpResponse));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSocket(socket);
        }
    }

    public Request buildRequestFromInputStream(InputStream requestInputStream) throws IOException {
        return RequestParser.parseRequest(requestInputStream);
    }

    public OutputStream returnResponseToOutputStream(byte[] httpResponse, OutputStream outputStream) throws IOException {
        OutputStream responseOutputStream = new DataOutputStream(outputStream);
        responseOutputStream.write(httpResponse);
        return responseOutputStream;
    }

    public void closeSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
