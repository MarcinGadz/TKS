package com.edu.tks.ports.view.service.rental;

import com.edu.tks.exception.InputExceptionView;
import com.edu.tks.exception.NotFoundExceptionView;
import com.edu.tks.model.RentalView;

public interface AddRentalUseCase {
    RentalView createRental(String clientID, String recordID) throws InputExceptionView, NotFoundExceptionView;
}
