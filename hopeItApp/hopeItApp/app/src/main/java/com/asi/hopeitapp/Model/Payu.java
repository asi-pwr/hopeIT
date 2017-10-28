package com.asi.hopeitapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by sgorawski on 28.10.17.
 */

public class Payu implements Serializable
{

    @SerializedName("email")
    @Expose
    private String email;
    private final static long serialVersionUID = -659062287290162179L;

    public Payu(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
