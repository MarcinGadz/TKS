package com.edu.tks.repo.entity;

import com.edu.tks.repo.exception.InputException;
import com.edu.tks.repo.exception.PermissionException;
import com.edu.tks.repo.exception.RentalException;

import java.time.LocalDateTime;
import java.util.UUID;

public class RentalEntity {
    private final UUID rentalID;
    private final UUID clientID;
    private final UUID recordID;

    private final LocalDateTime rentDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnDate;

    private boolean active;

    public RentalEntity(UUID rentalID, UUID clientID, UUID recordID, LocalDateTime rentDate,
                        LocalDateTime expectedReturnDate, LocalDateTime actualReturnDate, boolean active) {
        this.rentalID = rentalID;

        this.clientID = clientID;
        this.recordID = recordID;

        this.rentDate = rentDate;
        this.active = active;

        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
    }

    public RentalEntity(UUID rentalID, UUID clientID, UUID recordID) {
        this(rentalID, clientID, recordID, LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, true);
    }

    public RentalEntity(UUID clientID, UUID recordID) {
        this(UUID.randomUUID(), clientID, recordID);
    }

    public UUID getRentalID() {
        return rentalID;
    }

    public UUID getClientID() {
        return clientID;
    }

    public UUID getRecordID() {
        return recordID;
    }

    public LocalDateTime getRentDate() {
        return rentDate;
    }

    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void extendReturnDays(int days) throws RentalException {
        if (days <= 0) {
            throw new RentalException("Wrong number of days");
        }

        this.expectedReturnDate = expectedReturnDate.plusDays(days);
    }


}
