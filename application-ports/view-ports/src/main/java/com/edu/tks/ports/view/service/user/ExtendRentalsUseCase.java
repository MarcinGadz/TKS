package com.edu.tks.ports.view.service.user;

import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.exception.SOAPPermissionException;
import com.edu.tks.exception.SOAPRentalException;

public interface ExtendRentalsUseCase {
    void extendRentReturnDays(String userId, int days) throws SOAPPermissionException, SOAPRentalException, SOAPNotFoundException;
}
