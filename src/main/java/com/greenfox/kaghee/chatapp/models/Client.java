package com.greenfox.kaghee.chatapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class Client {
    String id;

    public Client() {
    }

    public Client(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
