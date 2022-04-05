package com.edu.tks.entity;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.PermissionException;
import com.edu.tks.exception.RentalException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserEntity implements Cloneable {
    private UUID userID;
    private String login;
    private UserTypeEntity type;
    private boolean active;

    private final List<RentalEntity> rentalEntities = new ArrayList<>();
    private final List<RentalEntity> archiveRentalEntities = new ArrayList<>();
    private final List<RecordEntity> cart = new ArrayList<>();

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

    // CART METHODS

    public List<RecordEntity> getCart() {
        return this.cart;
    }

    public void addToCart(RecordEntity recordEntity) throws RentalException {
        if (!this.active) {
            throw new RentalException("shop.User is not active");
        }

        if (recordEntity.isRented()) {
            throw new RentalException("shop.Record already rented");
        }
        cart.add(recordEntity);
    }

    public void removeFromCart(RecordEntity recordEntity) throws RentalException {
        if (!this.active) {
            throw new RentalException("shop.User is not active");
        }

        if (!cart.contains(recordEntity)) {
            throw new RentalException("shop.Record not in cart");
        }
        cart.remove(recordEntity);
    }

    public void clearCart() throws RentalException {
        if (!this.active) {
            throw new RentalException("shop.User is not active");
        }

        cart.clear();
    }


    // RENTALS METHODS

    public List<RentalEntity> getRentals() {
        return this.rentalEntities;
    }

    public List<RentalEntity> getArchiveRentals() {
        return archiveRentalEntities;
    }

    public List<RentalEntity> rentCart(UserEntity renter) throws PermissionException, InputException, RentalException {
        List<RentalEntity> newRentalEntities = new ArrayList<>();

        if (!this.active) {
            throw new RentalException("User is not active");
        }

        if (renter.getType() != UserTypeEntity.RENTER) {
            throw new PermissionException("Indicated renter has no permissions to do this operation");
        }

        for (RecordEntity recordEntity : this.cart) {
            RentalEntity newRent = new RentalEntity(this, renter, recordEntity);
            rentalEntities.add(newRent);
            newRentalEntities.add(newRent);
        }

        this.clearCart();
        return newRentalEntities;
    }

    public void clearRentals(UserEntity renter) throws PermissionException, RentalException {
        if (!this.active) {
            throw new RentalException("shop.User is not active");
        }

        if (renter.getType() != UserTypeEntity.RENTER) {
            throw new PermissionException("Indicated renter has no permissions to do this operation");
        }

        this.archiveRentalEntities.addAll(this.rentalEntities);
        rentalEntities.clear();
    }

    public void extendRentReturnDays(UserEntity renter, int days) throws RentalException, PermissionException {
        if (!this.active) {
            throw new RentalException("shop.User is not active");
        }

        if (renter.getType() != UserTypeEntity.RENTER) {
            throw new PermissionException("Indicated renter has no permissions to do this operation");
        }

        for (RentalEntity rentalEntity : this.rentalEntities) {
            rentalEntity.extendReturnDays(days);
        }
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
