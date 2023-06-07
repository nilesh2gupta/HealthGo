package com.example.healthgo;



import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BookBedDesign extends AppCompatActivity {
    String hospitalname;
    String totalbed;
    String availablebed;
    String status;
    Button bookbed;

    public BookBedDesign(String hospitalname, String totalbed, String availablebed, String status) {
        this.hospitalname = hospitalname;
        this.totalbed = totalbed;
        this.availablebed = availablebed;
        this.status = status;
    }




    public BookBedDesign() {
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getTotalbed() {
        return totalbed;
    }

    public void setTotalbed(String totalbed) {
        this.totalbed = totalbed;
    }

    public String getAvailablebed() {
        return availablebed;
    }

    public void setAvailablebed(String availablebed) {
        this.availablebed = availablebed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
