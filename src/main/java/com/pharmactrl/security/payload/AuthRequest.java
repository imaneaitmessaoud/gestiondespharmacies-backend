package com.pharmactrl.security.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthRequest {
    private String email;

    @JsonProperty("motDePasse")
    private String motDePasse;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
