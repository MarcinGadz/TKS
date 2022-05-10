package com.edu.tks.repo.repositories;

import com.edu.tks.repo.entity.RentalEntity;
import com.edu.tks.repo.exception.InputException;
import com.edu.tks.repo.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class RentalRepository {
    private final List<RentalEntity> rentals = new ArrayList<>();
    private final List<RentalEntity> archiveRentalEntities = new ArrayList<>();

    public RentalRepository() {
        try {
            RentalEntity activeRental = new RentalEntity(
                    UUID.fromString("418d0406-e040-408a-abf3-7788db83b889"),
                    UUID.fromString("cebbee82-2398-4dc2-a94d-a4c863286ff0"),
                    UUID.fromString("02cf35bf-d025-440b-a6ec-17cc6c77b021"),
                    LocalDateTime.of(2022, 5, 3, 13, 36),
                    LocalDateTime.of(2022, 5, 10, 13, 36),
                    null,
                    true);
            rentals.add(activeRental);
            activeRental = new RentalEntity(
                    UUID.fromString("53d3b654-c542-11ec-9d64-0242ac120002"),
                    UUID.fromString("c6f3fbf7-135b-498e-8154-3a5b9d291145"),
                    UUID.fromString("3e3719e5-8689-4e65-883f-4cd06cae7195"),
                    LocalDateTime.of(2022, 1, 12, 15, 23),
                    LocalDateTime.of(2022, 1, 19, 15, 23),
                    null,
                    true);
            rentals.add(activeRental);
            RentalEntity inactiveRental = new RentalEntity(
                    UUID.fromString("1fa7cdf2-2b0c-428b-bbf7-a593d56e3b74"),
                    UUID.fromString("c6f3fbf7-135b-498e-8154-3a5b9d291145"),
                    UUID.fromString("3e3719e5-8689-4e65-883f-4cd06cae7195"),
                    LocalDateTime.of(2021, 2, 13, 16, 13),
                    LocalDateTime.of(2021, 2, 20, 16, 13),
                    LocalDateTime.of(2021, 2, 20, 15, 45),
                    false);
            archiveRentalEntities.add(inactiveRental);
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
            throw new NotFoundException("RentalEntity not found");
        }
        return rental;
    }

    public void appendRentalEntity(RentalEntity rental) {
        rentals.add(rental);
    }

    public void appendRentalEntities(List<RentalEntity> rents) {
        rentals.addAll(rents);
    }

    public RentalEntity archiveRentalEntity(String rentalID) throws NotFoundException, InputException {
        RentalEntity rental = this.getRentalEntityByID(rentalID);
        archiveRentalEntities.add(rental);
        rental.setActive(false);
        rental.setActualReturnDate(LocalDateTime.now());
        rentals.remove(rental);
        return rental;
    }
}
