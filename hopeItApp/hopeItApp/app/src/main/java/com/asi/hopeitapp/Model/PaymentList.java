
package com.asi.hopeitapp.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentList implements Serializable
{
    @SerializedName("payments")
    @Expose
    private List<Payment> payments = null;
    private final static long serialVersionUID = -8743647782440692005L;

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

}
