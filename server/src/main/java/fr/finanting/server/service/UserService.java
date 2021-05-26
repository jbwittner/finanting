package fr.finanting.server.service;

import fr.finanting.codegen.model.PasswordUpdateParameter;
import fr.finanting.codegen.model.UserDTO;
import fr.finanting.codegen.model.UserRegistrationParameter;
import fr.finanting.codegen.model.UserUpdateParameter;
import fr.finanting.server.exception.BadPasswordException;
import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;

public interface UserService {

    UserDTO registerNewAccount(UserRegistrationParameter userRegistrationParameter)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException;

    UserDTO getAccountInformation(String userName);

    UserDTO updateAccountInformation(UserUpdateParameter userUpdateParameter, String userName)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException;

    void updatePassword(PasswordUpdateParameter passwordUpdateParameter, String userName)
            throws BadPasswordException;
    
}
