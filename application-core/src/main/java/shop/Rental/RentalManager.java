package shop.Rental;

import org.springframework.stereotype.Service;
import shop.Exceptions.InputException;
import shop.Exceptions.NotFoundException;
import shop.Exceptions.RentalException;
import shop.Record.Record;
import shop.Record.RecordRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentalManager {
    RentalRepository repository = new RentalRepository();

    public Rental getRentalByID(String rentalID) throws NotFoundException {
        return repository.getRentalByID(rentalID);
    }

    public List<Rental> getAllRentals() {
        return repository.getAllRentals();
    }

    public List<Rental> getAllArchiveRentals() {
        return repository.getAllArchiveRentals();
    }

    public synchronized void appendRental(Rental rental){
        repository.appendRental(rental);
    }

    public synchronized void appendRentals(List<Rental> rents){
        repository.appendRentals(rents);
    }

    public synchronized void archiveRental(String rentalID) throws RentalException, NotFoundException, InputException {
        repository.archiveRental(rentalID);
    }

    public synchronized void archiveRentals(List<Rental> rents) throws RentalException, NotFoundException, InputException {
        repository.archiveRentals(rents);
    }

}
