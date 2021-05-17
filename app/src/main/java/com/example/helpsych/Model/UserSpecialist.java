package com.example.helpsych.Model;

public class UserSpecialist {

    public String uid;
    public String id;
    public String usertype;
    public String name;
    public String lastName;
    public String sex;
    public String birthdate;
    public String image;
    public String description;
    public String city;
    public String country;
    public String linkedin;

    public UserSpecialist(String uid, String id, String usertype, String name, String lastName, String sex, String birthdate, String image, String description, String city, String country, String linkedin) {
        this.uid = uid;
        this.id = id;
        this.usertype = usertype;
        this.name = name;
        this.lastName = lastName;
        this.sex = sex;
        this.birthdate = birthdate;
        this.image = image;
        this.description = description;
        this.city = city;
        this.country = country;
        this.linkedin = linkedin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }


}



