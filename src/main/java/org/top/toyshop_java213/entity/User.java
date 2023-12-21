package org.top.toyshop_java213.entity;

import jakarta.persistence.*;

import java.util.Set;

// User - таблица пользователей
@Entity
@Table(name = "user_t")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login_f", nullable = false)
    private String login;

    @Column(name = "password_f", nullable = false)
    private String password;    // пароль в захешированном виде

    @Column(name = "role_f", nullable = false)
    private String role;

    @OneToMany(mappedBy = "user")
    private Set<Order> orderSet;

    public User() {
        id = 0;
        login = "";
        password = "";
        role = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
