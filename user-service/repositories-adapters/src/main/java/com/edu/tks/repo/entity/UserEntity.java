package com.edu.tks.repo.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

public class UserEntity implements Cloneable {
    private UUID userID;
    private String login;
    private UserTypeEntity type;
    private boolean active;

    public UserEntity() {
    }

    public UserEntity(String login, UserTypeEntity type) {
        this(UUID.randomUUID(), login, false, type);
    }

    public UserEntity(UUID userID, String login, boolean isActive, UserTypeEntity type) {
        this.userID = userID;
        this.login = login;
        this.active = isActive;
        this.type = type;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserTypeEntity getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserEntity userEntity = (UserEntity) o;

        return new EqualsBuilder()
                .append(userID, userEntity.userID)
                .isEquals();
    }

    @Override
    public UserEntity clone() throws CloneNotSupportedException {
        UserEntity u = (UserEntity)super.clone();
        return u;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userID)
                .toHashCode();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    public boolean isActive() {
        return active;
    }
}
