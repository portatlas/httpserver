package com.portatlas;

import com.portatlas.helpers.writers.ConsoleWriter;
import com.portatlas.request.Request;
import com.portatlas.request.RequestParser;
import com.portatlas.response.Response;
import com.portatlas.response.ResponseSerializer;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private Directory directory;

    public ClientThread(Socket socket, Directory directory) {
        this.socket = socket;
        this.directory = directory;
    }

    public void run() {
        try {
            InputStream requestInputStream = socket.getInputStream();
            Request request = buildRequestFromInputStream(requestInputStream);
            Response response = Controller.processRequest(request, directory);
            returnResponseToOutputStream(ResponseSerializer.serialize(response), socket.getOutputStream());
            ConsoleWriter.write(response.getStatusLine());
        } catch (Exception e) {
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
