package by.batseko.library.entity;

import by.batseko.library.builder.user.UserBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private Role role;
    private String login;
    private String password;
    private boolean isBanned;
    private String firstName;
    private String lastName;
    private String passportSerialNumber;
    private String email;
    private String phoneNumber;
    private Timestamp registrationDate;
    private String address;

    public User(UserBuilder builder) {
        id = builder.getId();
        role = builder.getRole();
        login = builder.getLogin();
        password = builder.getPassword();
        firstName = builder.getFirstName();
        isBanned = builder.isBanned();
        lastName = builder.getLastName();
        passportSerialNumber = builder.getPassportSerialNumber();
        email = builder.getEmail();
        phoneNumber = builder.getPhoneNumber();
        registrationDate = builder.getRegistrationDate();
        address = builder.getAddress();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
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

    public String getPassportSerialNumber() {
        return passportSerialNumber;
    }

    public void setPassportSerialNumber(String passportSerialNumber) {
        this.passportSerialNumber = passportSerialNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                isBanned == user.isBanned &&
                Objects.equals(role, user.role) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(passportSerialNumber, user.passportSerialNumber) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(registrationDate, user.registrationDate) &&
                Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, login, password, isBanned, firstName, lastName, passportSerialNumber, email, phoneNumber, registrationDate, address);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isBanned=" + isBanned +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passportSerialNumber='" + passportSerialNumber + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", registrationDate=" + registrationDate +
                ", street='" + address + '\'' +
                '}';
    }
}
