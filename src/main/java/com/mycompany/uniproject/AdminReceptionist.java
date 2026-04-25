package com.mycompany.uniproject;
import java.util.*;

public abstract class AdminReceptionist {

    private String username;
    private String password;
    private Date birthDate;
    private int workingHours;
    private Role role;

    public AdminReceptionist(String username, String password, Role role) {
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
    public void setUsername(String username) { this.username = username; }
    public void setWorkingHours(int workingHours) { this.workingHours = workingHours; }
    public String getPassword() { return password; }
    public String getUsername() { return username; }
    public Date getBirthDate() { return birthDate; }
    public int getWorkingHours() { return workingHours; }
    public Role getRole() { return role; }

    public abstract void makeBehaviour();
}
