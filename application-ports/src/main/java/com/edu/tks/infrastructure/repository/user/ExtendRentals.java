package com.edu.tks.infrastructure.repository.user;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.PermissionException;
import com.edu.tks.exception.RentalException;

public interface ExtendRentals {
    void extendRentReturnDays(String renterId, String userId, int days) throws PermissionException, RentalException, NotFoundException;
}
