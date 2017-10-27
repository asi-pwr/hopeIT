package com.asi.hopeitapp.Model;

/**
 * Created by sgorawski on 28.10.17.
 */

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable
{

    @SerializedName("payu")
    @Expose
    private Payu payu;
    private final static long serialVersionUID = -7984576716430741767L;

    public User(Payu payu) {
        this.payu = payu;
    }

    public Payu getPayu() {
        return payu;
    }

    public void setPayu(Payu payu) {
        this.payu = payu;
    }

}
