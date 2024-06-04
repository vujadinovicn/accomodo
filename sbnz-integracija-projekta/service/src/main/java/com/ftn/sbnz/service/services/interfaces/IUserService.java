package com.ftn.sbnz.service.services.interfaces;

import com.ftn.sbnz.model.models.User;

public interface IUserService {

    User getUserByEmail(String email);
    
}
