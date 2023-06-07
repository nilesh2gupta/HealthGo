package com.example.healthgo;

public class DoctorProfileInfo {
    String Name,Speciality,Qualification,Address,Country,State,City,Mobile,Email,Age,Bloodgrp,Gender;

    public DoctorProfileInfo(String name, String speciality, String qualification, String address, String country, String state, String city, String mobile, String email, String age, String bloodgrp, String gender) {
        Name = name;
        Speciality = speciality;
        Qualification = qualification;
        Address = address;
        Country = country;
        State = state;
        City = city;
        Mobile = mobile;
        Email = email;
        Age = age;
        Bloodgrp = bloodgrp;
        Gender = gender;
    }

    public DoctorProfileInfo() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBloodgrp() {
        return Bloodgrp;
    }

    public void setBloodgrp(String bloodgrp) {
        Bloodgrp = bloodgrp;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
