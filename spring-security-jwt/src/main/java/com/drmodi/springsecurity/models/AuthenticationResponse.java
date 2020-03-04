package com.drmodi.springsecurity.models;

public class AuthenticationResponse {

    private final String generatedJwt;

    public AuthenticationResponse(String generatedJwt) {
        this.generatedJwt = generatedJwt;
    }

    public String getGeneratedJwt() {
        return generatedJwt;
    }
}
