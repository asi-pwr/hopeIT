package com.asi.hopeitapp.Model;

/**
 * Created by sgorawski on 28.10.17.
 */


import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationWrapper implements Serializable
{

    @SerializedName("donation")
    @Expose
    private Donation donation;
    private final static long serialVersionUID = 1055222736397162699L;

    public DonationWrapper(Donation donation) {
        this.donation = donation;
    }

    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

}