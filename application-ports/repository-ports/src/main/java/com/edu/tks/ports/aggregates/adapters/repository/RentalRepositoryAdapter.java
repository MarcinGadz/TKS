package com.edu.tks.ports.aggregates.adapters.repository;

import com.edu.tks.ports.aggregates.converters.repository.RentalConverter;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.ports.infrastructure.repository.rental.AddRental;
import com.edu.tks.ports.infrastructure.repository.rental.ArchiveRentals;
import com.edu.tks.ports.infrastructure.repository.rental.GetRentals;
import com.edu.tks.rental.Rental;
import com.edu.tks.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalRepositoryAdapter implements GetRentals, AddRental, ArchiveRentals {

    @Autowired
    private RentalRepository repo;

    @Override
    public void appendRental(Rental rental) {
        repo.appendRentalEntity(RentalConverter.convertRentalToRentalEntity(rental));
    }

    @Override
    public void appendRentals(List<Rental> rents) {
        repo.appendRentalEntities(
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
    public List<Rental> getAllRentals() {
        return repo.getAllRentalEntities().stream()
                .map(RentalConverter::convertRentalEntityToRental)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> getAllArchiveRentals() {
        return repo.getAllArchiveRentalEntities().stream()
                .map(RentalConverter::convertRentalEntityToRental)
                .collect(Collectors.toList());
    }

    @Override
    public Rental getRentalByID(String rentalID) throws NotFoundException {
        return RentalConverter.convertRentalEntityToRental(repo.getRentalEntityByID(rentalID));
    }
}
