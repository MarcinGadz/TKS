package com.edu.tks.user;

import com.edu.tks.exception.InputException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

public class User implements Cloneable {
    private UUID userID;
    private String login;
    private UserType type;
    private boolean active = false;

    public User() {
    }

    public User(String login, UserType type) {
        this(UUID.randomUUID(), login, false, type);
    }

    public User(UUID userID, String login, boolean isActive, UserType type) {
        this.userID = userID;
        this.login = login;
        this.type = type;
        this.active = isActive;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", login='" + login + '\'' +
                ", type=" + type +
                ", active=" + active +
                '}';
    }

    public UUID getUserID() {
        return userID;
    }

    public String getLogin() {
        return login;
    }

    public Boolean isActive() {
        return active;
    }


    public void activate() throws InputException {
        if (this.active) {
            throw new InputException("shop.User already activated");
        }
        this.active = true;
    }

    public void deactivate() throws InputException {
        if (!this.active) {
            throw new InputException("shop.User already deactivated");
        }
        this.active = false;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(userID, user.userID)
                .isEquals();
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        User u = (User) super.clone();

        return u;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userID)
                .toHashCode();
    }

    public void setUserId(UUID clientID) {
        this.userID = clientID;
    }
}
