package com.example.owppharmacy.models;

import com.example.owppharmacy.enums.ERole;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Account {
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private LocalDateTime registrationDate;
    private ERole userRole;
    private boolean isBlocked;

    public Account() {
    }

    public Account(int id, String username, String password, String email, String firstName, String lastName, LocalDate dateOfBirth, String address, String phoneNumber, LocalDateTime registrationDate, ERole userRole, boolean isBlocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.userRole = userRole;
        this.isBlocked = isBlocked;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public ERole getUserRole() {
        return userRole;
    }

    public void setUserRole(ERole userRole) {
        this.userRole = userRole;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
