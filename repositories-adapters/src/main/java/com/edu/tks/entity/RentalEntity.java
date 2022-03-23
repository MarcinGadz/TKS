package com.edu.tks.entity;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.PermissionException;
import com.edu.tks.exception.RentalException;

import java.time.LocalDateTime;
import java.util.UUID;

public class RentalEntity {
    private final UUID rentalID;
    private final String clientID;
    private final String renterID;
    private final String recordID;

    private transient final UserEntity client;
    private transient final UserEntity renter;
    private transient final RecordEntity recordEntity;

    private final LocalDateTime rentDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnDate;

    public RentalEntity(UserEntity client, UserEntity renter, RecordEntity recordEntity) throws PermissionException, InputException {
        this(UUID.randomUUID(), client, renter, recordEntity);
    }

    public RentalEntity(UUID rentalID, UserEntity client, UserEntity renter, RecordEntity recordEntity) throws PermissionException, InputException {
        this.rentalID = rentalID;
        if (renter.getType() != UserTypeEntity.RENTER) {
            throw new PermissionException("Indicated renter has no permissions to do this operation");
        }

        this.clientID = client.getUserID().toString();
        this.renterID = renter.getUserID().toString();
        this.recordID = recordEntity.getRecordID().toString();

        this.client = client;
        this.renter = renter;
        this.recordEntity = recordEntity;

        this.rentDate = LocalDateTime.now();
        this.recordEntity.rent(this);

        this.expectedReturnDate = this.rentDate.plusDays(7);
    }

    public UUID getRentalID() {
        return rentalID;
    }

    public UserEntity getClient() {
        return client;
    }

    public UserEntity getRenter() {
        return renter;
    }

    public String getClientID() {
        return clientID;
    }

    public String getRenterID() {
        return renterID;
    }

    public String getRecordID() {
        return recordID;
    }

    public LocalDateTime getRentDate() {
        return rentDate;
    }

    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public RecordEntity getRecord() {
        return recordEntity;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public void returnRecord() throws InputException {
        this.actualReturnDate = LocalDateTime.now();
        this.recordEntity.release();
    }

    public void extendReturnDays(int days) throws RentalException {
        if (days <= 0) {
            throw new RentalException("Wrong number of days");
        }

        this.expectedReturnDate = expectedReturnDate.plusDays(days);
    }


}
