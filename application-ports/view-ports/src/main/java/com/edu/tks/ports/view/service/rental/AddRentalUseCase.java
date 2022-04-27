package com.edu.tks.ports.view.service.rental;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.rest.exception.InputExceptionView;
import com.edu.tks.rest.exception.NotFoundExceptionView;
import com.edu.tks.rest.model.RentalView;

public interface AddRentalUseCase {
    RentalView createRental(String clientID, String recordID) throws InputExceptionView, NotFoundExceptionView;
}
