package com.ftn.sbnz.service.services.interfaces;

import java.util.List;

import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.dtos.AdminUsersDTO;
import com.ftn.sbnz.service.dtos.TravelerDetailsDTO;

public interface IUserService {

    User getUserByEmail(String email);
    public User getCurrentUser();
    List<Listing> addTravelerLoggedInEvent(User user);
    List<AdminUsersDTO> getForAdmin();
    void unblock(String email);
    void block(String email);
    TravelerDetailsDTO getDetails();
}
