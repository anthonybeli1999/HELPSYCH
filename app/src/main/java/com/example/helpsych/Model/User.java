package com.example.helpsych.Model;

public class User {
    /*public int id;
    public int id_tipo;
    public String nombre;
    public String apellido;
    public String telefono;
    public String ciudad;
    public String pais;
    public String email;
    public String imagen;
    public String password;
    public String genero;
    public String fecha_nacimiento;
    public String fecha_creacion;
    public String fecha_modificacion;*/

    public String uid;
    public String id;
    public String usertype;
    public String name;
    public String lastName;
    public String sex;
    public String birthdate;
    public String image;

    public String getImage() {
        return image;
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
}
