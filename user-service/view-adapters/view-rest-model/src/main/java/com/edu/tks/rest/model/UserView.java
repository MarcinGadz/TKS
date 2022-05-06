package com.edu.tks.rest.model;

import com.edu.tks.rest.exception.InputExceptionView;
import com.edu.tks.rest.exception.RentalExceptionView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserView implements Cloneable {
    private UUID userID;
    private String login;
    private UserTypeView type;
    private boolean active = false;

    public UserView() {
    }

    public UserView(String login, UserTypeView type) {
        this(UUID.randomUUID(), login, false, type);
    }

    public UserView(UUID userID, String login, boolean isActive, UserTypeView type) {
        this.userID = userID;
        this.login = login;
        this.type = type;
        this.active = isActive;
    }

    @Override
    public String toString() {
        return "UserView{" +
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


    public void activate() throws InputExceptionView {
        if (this.active) {
            throw new InputExceptionView("shop.User already activated");
        }
        this.active = true;
    }

    public void deactivate() throws InputExceptionView {
        if (!this.active) {
            throw new InputExceptionView("shop.User already deactivated");
        }
        this.active = false;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserTypeView getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserView user = (UserView) o;

        return new EqualsBuilder()
                .append(userID, user.userID)
                .isEquals();
    }

    @Override
    public UserView clone() throws CloneNotSupportedException {
        UserView u = (UserView) super.clone();

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
