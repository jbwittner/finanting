package fr.finanting.server.service;

import java.security.Principal;

import fr.finanting.server.dto.UserDTO;
import fr.finanting.server.exception.BadPasswordException;
import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.parameter.PasswordUpdateParameter;
import fr.finanting.server.parameter.UserRegisterParameter;
import fr.finanting.server.parameter.UserUpdateParameter;

/**
 * Service for users
 */
public interface UserService {

    /**
     * Method used to register a new account
     */
    public UserDTO registerNewAccount(UserRegisterParameter userRegisterParameter) throws UserEmailAlreadyExistException, UserNameAlreadyExistException;

    /**
     * Method used to get account informations
     */
    public UserDTO getAccountInformations(String userName);

    /**
     * Method used to update account informations
     */
    public UserDTO updateAccountInformations(UserUpdateParameter userUpdateParameter, String userName);

    /**
     * Method used to update account password
     */
    public void updatePassword(PasswordUpdateParameter passwordUpdateParameter, String userName) throws BadPasswordException;
    
}
