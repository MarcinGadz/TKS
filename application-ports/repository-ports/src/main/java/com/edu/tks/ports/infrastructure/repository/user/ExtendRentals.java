package com.edu.tks.ports.infrastructure.repository.user;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.PermissionException;
import com.edu.tks.exception.RentalException;

public interface ExtendRentals {
    void extendRentReturnDays(String userId, int days) throws PermissionException, RentalException, NotFoundException;
}
