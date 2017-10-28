
package com.asi.hopeitapp.Model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Payment extends SugarRecord<Payment> implements Serializable
{
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("payment_uuid")
    @Expose
    private String paymentUuid;
    @SerializedName("patient_id")
    @Expose
    private Integer patientId;
    @SerializedName("donation_type_id")
    @Expose
    private Integer donationTypeId;
    private final static long serialVersionUID = 413139437201673283L;

    public Payment(){

    }

    public Payment(Integer amount, String status, String orderId, String paymentUuid, Integer patientId, Integer donationTypeId) {
        this.amount = amount;
        this.status = status;
        this.orderId = orderId;
        this.paymentUuid = paymentUuid;
        this.patientId = patientId;
        this.donationTypeId = donationTypeId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentUuid() {
        return paymentUuid;
    }

    public void setPaymentUuid(String paymentUuid) {
        this.paymentUuid = paymentUuid;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDonationTypeId() {
        return donationTypeId;
    }

    public void setDonationTypeId(Integer donationTypeId) {
        this.donationTypeId = donationTypeId;
    }

}
