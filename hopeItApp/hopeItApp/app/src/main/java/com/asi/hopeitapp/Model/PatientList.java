
package com.asi.hopeitapp.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientList implements Serializable
{
    @SerializedName("patients")
    @Expose
    private List<Patient> patients = null;
    private final static long serialVersionUID = 5019838154694605520L;

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

}
