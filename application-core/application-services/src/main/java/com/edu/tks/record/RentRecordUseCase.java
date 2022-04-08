package com.edu.tks.record;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;

public interface RentRecordUseCase {
    void rent(String recordID) throws InputException, NotFoundException;
}
