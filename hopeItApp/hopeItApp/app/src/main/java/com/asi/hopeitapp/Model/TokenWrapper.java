
package com.asi.hopeitapp.Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenWrapper implements Serializable
{

    @SerializedName("token")
    @Expose
    private Token token = null;
    private final static long serialVersionUID = 4999647780784390385L;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

}
