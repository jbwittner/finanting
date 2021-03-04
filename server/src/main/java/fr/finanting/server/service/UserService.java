package fr.finanting.server.service;

import fr.finanting.server.dto.UserDTO;
import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.parameter.UserRegisterParameter;

public interface UserService {

    public UserDTO registerNewAccount(UserRegisterParameter userRegisterParameter) throws UserEmailAlreadyExistException, UserNameAlreadyExistException;
    
}
