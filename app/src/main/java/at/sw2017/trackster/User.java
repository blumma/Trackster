package at.sw2017.trackster;

import java.util.Date;

/**
 * Created by mblum on 04.05.2017.
 */

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String pwd;
    private Date createdAt;

    public User() {

    }

    public User(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    public User(int id, String firstName, String lastName, String email, String pwd, Date createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pwd = pwd;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

