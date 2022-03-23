package com.edu.tks.aggregates.adapters;

import com.edu.tks.aggregates.converters.RentalConverter;
import com.edu.tks.entity.RentalEntity;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.infrastructure.repository.rental.AddRental;
import com.edu.tks.infrastructure.repository.rental.ArchiveRentals;
import com.edu.tks.infrastructure.repository.rental.GetRentals;
import com.edu.tks.rental.Rental;
import com.edu.tks.repositories.RentalRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RentalRepositoryAdapter implements GetRentals, AddRental, ArchiveRentals {

    private final RentalRepository repo = new RentalRepository();

    @Override
    public void appendRental(Rental rental) {
        repo.appendRentalEntity(RentalConverter.convertRentalToRentalEntity(rental));
    }

    @Override
    public void appendRentals(List<Rental> rents) {
        repo.appendRentalEntitys(
                rents.stream()
                        .map(RentalConverter::convertRentalToRentalEntity)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void archiveRental(String rentalID) throws NotFoundException, InputException {
        repo.archiveRentalEntity(rentalID);
    }

    @Override
    public void archiveRentals(List<Rental> rents) throws InputException {
        repo.archiveRentalEntitys(
            rents.stream()
                    .map(RentalConverter::convertRentalToRentalEntity)
                    .collect(Collectors.toList())
        );
    }

    @Override
    public List<Rental> getAllRentals() {
        return repo.getAllRentalEntitys().stream()
                .map(RentalConverter::convertRentalEntityToRental)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> getAllArchiveRentals() {
        return repo.getAllArchiveRentalEntitys().stream()
                .map(RentalConverter::convertRentalEntityToRental)
                .collect(Collectors.toList());
    }

    @Override
    public Rental getRentalByID(String rentalID) throws NotFoundException {
        return RentalConverter.convertRentalEntityToRental(repo.getRentalEntityByID(rentalID));
    }
}
