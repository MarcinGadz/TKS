package com.edu.tks.ports.view.service.user;

import com.edu.tks.exception.BasicSOAPException;

public interface RemoveUserUseCase {
    void removeUser(String userid) throws BasicSOAPException;
}
