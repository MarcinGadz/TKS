package com.edu.tks.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.PermissionException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.record.Record;
import com.edu.tks.rental.Rental;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Cloneable {
    private final UUID userID;
    private String login;
    private final UserType type;
    private Boolean active = true;

    @JsonIgnore
    private final List<Record> cart = new ArrayList<>();

    public User(String login, UserType type) {
        this(UUID.randomUUID(), login, type);
    }

    public User(UUID userID, String login, UserType type) {
        this.userID = userID;
        this.login = login;
        this.type = type;
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

    // CART METHODS

    public List<Record> getCart() {
        return this.cart;
    }

    public void addToCart(Record record) throws RentalException {
        if (!this.active) {
            throw new RentalException("shop.User is not active");
        }

        if (record.isRented()) {
            throw new RentalException("shop.Record already rented");
        }
        cart.add(record);
    }

    public void removeFromCart(Record record) throws RentalException {
        if (!this.active) {
            throw new RentalException("shop.User is not active");
        }

        if (!cart.contains(record)) {
            throw new RentalException("shop.Record not in cart");
        }
        cart.remove(record);
    }

    public void clearCart() throws RentalException {
        if (!this.active) {
            throw new RentalException("shop.User is not active");
        }

        cart.clear();
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

}
