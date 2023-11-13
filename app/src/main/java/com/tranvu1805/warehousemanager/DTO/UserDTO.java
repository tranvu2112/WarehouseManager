package com.tranvu1805.warehousemanager.DTO;

public class UserDTO {
    int id;
    String name, pass;
    int role;
    String email;

    public UserDTO() {
    }

    public UserDTO(String name, String pass, int role, String email) {
        this.name = name;
        this.pass = pass;
        this.role = role;
        this.email = email;
    }

    public UserDTO(int id, String name, String pass, int role, String email) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.role = role;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
