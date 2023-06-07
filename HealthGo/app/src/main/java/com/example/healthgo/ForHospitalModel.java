package com.example.healthgo;

public class ForHospitalModel {
  String Address ,AmbulanceNumber,Email,Name,Number,NumberOfDoctor;
  long Ambulance;
  int Beds;

  public ForHospitalModel(String Address, long Ambulance, String AmbulanceNumber, int Beds, String Email, String Name, String Number, String NumberOfDoctor) {
   this.Address = Address;
   this.Ambulance = Ambulance;
   this.AmbulanceNumber = AmbulanceNumber;
   this.Beds = Beds;
   this.Email = Email;
   this.Name = Name;
   this.Number = Number;
   this.NumberOfDoctor = NumberOfDoctor;
  }

  public ForHospitalModel() {
  }

  public String getAddress() {
    return Address;
  }

  public void setAddress(String Address) {
    this.Address = Address;
  }

  public long getAmbulance() {
    return Ambulance;
  }

  public void setAmbulance(long Ambulance) {
    this.Ambulance = Ambulance;
  }

  public String getAmbulanceNumber() {
    return AmbulanceNumber;
  }

  public void setAmbulanceNumber(String AmbulanceNumber) {
    this.AmbulanceNumber = AmbulanceNumber;
  }

  public int getBeds() {
    return Beds;
  }

  public void setBeds(int Beds) {
    this.Beds = Beds;
  }

  public String getEmail() {
    return Email;
  }

  public void setEmail(String Email) {
    this.Email = Email;
  }

  public String getName() {
    return Name;
  }

  public void setName(String Name) {
    this.Name = Name;
  }

  public String getNumber() {
    return Number;
  }

  public void setNumber(String Number) {
    this.Number = Number;
  }

  public String getNumberOfDoctor() {
    return NumberOfDoctor;
  }

  public void setNumberOfDoctor(String NumberOfDoctor) {
    this.NumberOfDoctor = NumberOfDoctor;
  }
}
