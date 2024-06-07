package com.ftn.sbnz.service.services.interfaces;

import java.util.List;

import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.User;

public interface IUserService {

    User getUserByEmail(String email);
    public User getCurrentUser();
    List<Listing> addTravelerLoggedInEvent(User user);
}
