package fr.finanting.server.service;

import fr.finanting.server.codegen.model.PasswordUpdateParameter;
import fr.finanting.server.codegen.model.UserDTO;
import fr.finanting.server.codegen.model.UserUpdateParameter;
import fr.finanting.server.exception.BadPasswordException;
import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.parameter.UserRegisterParameter;

public interface UserService {

    public UserDTO registerNewAccount(UserRegisterParameter userRegisterParameter)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException;

    public UserDTO getAccountInformations(String userName);

    public UserDTO updateAccountInformations(UserUpdateParameter userUpdateParameter, String userName)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException;

    public void updatePassword(PasswordUpdateParameter passwordUpdateParameter, String userName)
            throws BadPasswordException;
    
}
