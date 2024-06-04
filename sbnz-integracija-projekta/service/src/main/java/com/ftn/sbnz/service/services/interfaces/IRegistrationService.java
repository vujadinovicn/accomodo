package com.ftn.sbnz.service.services.interfaces;

import com.ftn.sbnz.service.dtos.AddUserDTO;

public interface IRegistrationService {
    void addUser(AddUserDTO dto);

    boolean doesUserExist(String email);
}
