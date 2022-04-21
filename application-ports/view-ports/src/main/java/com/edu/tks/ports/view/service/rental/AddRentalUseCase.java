package com.edu.tks.ports.view.service.rental;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.RentalView;

public interface AddRentalUseCase {
    RentalView createRental(String clientID, String recordID) throws SOAPInputException, SOAPNotFoundException;
}
