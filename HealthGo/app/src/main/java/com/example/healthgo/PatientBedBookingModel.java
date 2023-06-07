package com.example.healthgo;

public class PatientBedBookingModel {
    String Address,Age,Bookin_ID,Email,Feedback,Gender,Mobile_Number,Name,Need_Ambulance,Problem;

    public PatientBedBookingModel(String address, String age, String bookin_ID, String email, String feedback, String gender, String mobile_Number, String name, String need_Ambulance, String problem) {
        Address = address;
        Age = age;
        Bookin_ID = bookin_ID;
        Email = email;
        Feedback = feedback;
        Gender = gender;
        Mobile_Number = mobile_Number;
        Name = name;
        Need_Ambulance = need_Ambulance;
        Problem = problem;
    }

    public PatientBedBookingModel() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBookin_ID() {
        return Bookin_ID;
    }

    public void setBookin_ID(String bookin_ID) {
        Bookin_ID = bookin_ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNeed_Ambulance() {
        return Need_Ambulance;
    }

    public void setNeed_Ambulance(String need_Ambulance) {
        Need_Ambulance = need_Ambulance;
    }

    public String getProblem() {
        return Problem;
    }

    public void setProblem(String problem) {
        Problem = problem;
    }
}
