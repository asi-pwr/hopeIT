
package com.asi.hopeitapp.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenWraper implements Serializable
{

    @SerializedName("")
    @Expose
    private List<Token> token = null;
    private final static long serialVersionUID = 4999647780784390385L;

    public List<Token> getToken() {
        return token;
    }

    public void setToken(List<Token> token) {
        this.token = token;
    }

}
