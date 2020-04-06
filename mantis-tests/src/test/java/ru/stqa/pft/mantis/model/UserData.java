package ru.stqa.pft.mantis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;



@Entity
@Table  (name = "mantis_user_table")
public class UserData {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    public int getId () {
        return id;
    }

    public String getUsername () {
        return username;
    }

    public String getEmail () {
        return email;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getPassword () {
        return password;
    }

    @Override
    public String toString () {
        return "UserData{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserData userData = (UserData) o;

        if (id != userData.id) return false;
        if (username != null ? !username.equals(userData.username) : userData.username != null) return false;
        if (email != null ? !email.equals(userData.email) : userData.email != null) return false;
        return password != null ? password.equals(userData.password) : userData.password == null;
    }

    @Override
    public int hashCode () {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

}

