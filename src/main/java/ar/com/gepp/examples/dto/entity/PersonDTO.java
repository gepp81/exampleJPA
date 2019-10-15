package ar.com.gepp.examples.dto.entity;

import java.io.Serializable;

public class PersonDTO implements Serializable {

    private static final long serialVersionUID = -2509092728861310367L;

    private String firstname;

    private String surename;

    private Short age;

    private String username;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
