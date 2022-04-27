package com.edu.tks.ports.view.service.user;

import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.exception.SOAPPermissionException;
import com.edu.tks.exception.SOAPRentalException;
import com.edu.tks.rest.exception.NotFoundExceptionView;
import com.edu.tks.rest.exception.PermissionExceptionView;
import com.edu.tks.rest.exception.RentalExceptionView;

public interface ExtendRentalsUseCase {
    void extendRentReturnDays(String userId, int days) throws PermissionExceptionView, RentalExceptionView, NotFoundExceptionView;
}
