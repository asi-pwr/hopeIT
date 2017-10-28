package com.asi.hopeitapp.Model;

/**
 * Created by sgorawski on 28.10.17.
 */

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donation implements Serializable
{

    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("donation_type_id")
    @Expose
    private Integer donationTypeId;
    @SerializedName("patient_id")
    @Expose
    private Integer patientId;
    @SerializedName("payment_uuid")
    @Expose
    private String extOrderId;
    private final static long serialVersionUID = 955789531288644790L;

    public Donation(Integer amount, Integer donationTypeId, Integer patientId, String extOrderId) {
        this.amount = amount;
        this.donationTypeId = donationTypeId;
        this.patientId = patientId;
        this.extOrderId = extOrderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDonationTypeId() {
        return donationTypeId;
    }

    public void setDonationTypeId(Integer donationTypeId) {
        this.donationTypeId = donationTypeId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

}