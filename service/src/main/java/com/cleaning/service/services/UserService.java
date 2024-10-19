package com.cleaning.service.services;

import com.cleaning.service.dto.UserDTO;
import com.cleaning.service.dto.UserRegisterRequest;

import java.util.List;

public interface UserService {

    /**
     * Registrerar en ny användare i systemet.
     *
     * @param userRegisterRequest Objekt som innehåller användarens registreringsinformation.
     * @return {@code UserDto} Objekt som representerar den registrerade användaren.
     * @throws UserAlreadyExistsException Om en användare med samma användarnamn redan existerar.
     * @throws UserRegistrationException  Om ett fel inträffar under registreringsprocessen.
     */
    UserDTO registerUser(UserRegisterRequest userRegisterRequest);

     /**
      * Hämtar en lista över alla registrerade användare.
      * @return En lista av {@code UserDto} som innehåller information om alla användare.
      */
    List<UserDTO> getUsers();

}
