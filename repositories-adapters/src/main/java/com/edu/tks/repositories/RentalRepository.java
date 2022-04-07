package com.edu.tks.repositories;

import com.edu.tks.entity.RecordEntity;
import com.edu.tks.entity.RentalEntity;
import com.edu.tks.entity.UserEntity;
import com.edu.tks.entity.UserTypeEntity;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.PermissionException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class RentalRepository {
    private List<RentalEntity> rentals = new ArrayList<>();
    private List<RentalEntity> archiveRentalEntities = new ArrayList<>();

    public RentalRepository() {
        try {
            RentalEntity init = new RentalEntity(
                    UUID.fromString("02cf35bf-d025-440b-a6ec-17cc6c77b013"),
                    new UserEntity(UUID.fromString("cebbee82-2398-4dc2-a94d-a4c863286ff0"), "Eleanora", true, UserTypeEntity.CLIENT),
                    new UserEntity(UUID.fromString("bfac0aaa-e7d2-4e33-9fa9-8111b14bcd58"), "DiscoJanet", true, UserTypeEntity.RENTER),
                    new RecordEntity(UUID.fromString("02cf35bf-d025-440b-a6ec-17cc6c77b021"),"Moral Power", "Nothing but Lorde", "2020-03-04", false));
            rentals.add(init);
        } catch (Exception ignored) {}
    }

    public List<RentalEntity> getAllRentalEntities() {
        return rentals;
    }

    public List<RentalEntity> getAllArchiveRentalEntities() {
        return archiveRentalEntities;
    }

    public RentalEntity getRentalEntityByID(String rentalID) throws NotFoundException {
        RentalEntity rental = rentals.stream()
                .filter( r -> rentalID.equals(r.getRentalID().toString()))
                .findFirst()
                .orElse(null);
        if (rental == null) {
            throw new NotFoundException("RecordEntity not found");
        }
        return rental;
    }

    public void appendRentalEntity(RentalEntity rental) {
        rentals.add(rental);
    }

    public void appendRentalEntities(List<RentalEntity> rents) {
        rentals.addAll(rents);
    }

    public void archiveRentalEntity(String rentalID) throws NotFoundException, InputException {
        RentalEntity rental = this.getRentalEntityByID(rentalID);

        rental.returnRecord();
        archiveRentalEntities.add(rental);
        rentals.remove(rental);
    }

    public void archiveRentalEntities(List<RentalEntity> rents) throws InputException {
        for (RentalEntity rental : rentals) {
            rental.returnRecord();
        }
        rentals.removeAll(rents);
        archiveRentalEntities.addAll(rents);
    }
}
