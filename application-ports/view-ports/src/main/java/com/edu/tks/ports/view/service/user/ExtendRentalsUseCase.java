package com.edu.tks.ports.view.service.user;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.PermissionException;
import com.edu.tks.exception.RentalException;

public interface ExtendRentalsUseCase {
    void extendRentReturnDays(String userId, int days) throws PermissionException, RentalException, NotFoundException;
}
