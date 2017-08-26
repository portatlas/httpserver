package com.portatlas.helpers.writers;

public class EndpointWriter {
    private String storedInfo = "";

    public EndpointWriter() {
        this.storedInfo = "";
    }

    public String getMessage() {
        return storedInfo;
    }

    public void appendToMessage(String content) {
        storedInfo += content + "\n";
    }

    public void storeInput(String formInput) {
        storedInfo = formInput;
    }

    public void deleteStoredInfo() {
        storedInfo = "";
    }
}
