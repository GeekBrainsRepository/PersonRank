package ru.infologic.model;

import javax.persistence.*;

@Entity
@Table(name = "Users", schema = "personrank", catalog = "")
public class UsersEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "login")
    private String login;

    @Basic
    @Column(name = "password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
