package com.edu.tks.ports.view.service.user;

import com.edu.tks.exception.NotFoundExceptionView;
import com.edu.tks.exception.PermissionExceptionView;
import com.edu.tks.exception.RentalExceptionView;

public interface ExtendRentalsUseCase {
    void extendRentReturnDays(String userId, int days) throws PermissionExceptionView, RentalExceptionView, NotFoundExceptionView;
}
