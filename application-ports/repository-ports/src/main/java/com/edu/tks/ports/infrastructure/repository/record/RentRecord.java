package com.edu.tks.ports.infrastructure.repository.record;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;

public interface RentRecord {
    void rent(String recordID) throws InputException, NotFoundException;
}
