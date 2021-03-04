package fr.finanting.server.service.implementation;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.finanting.server.dto.UserDTO;
import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.UserRegisterParameter;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO registerNewAccount(UserRegisterParameter userRegisterParameter)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        
        if(this.userRepository.existsByEmail(userRegisterParameter.getEmail())){
            throw new UserEmailAlreadyExistException(userRegisterParameter.getEmail());
        } else if (this.userRepository.existsByUsername(userRegisterParameter.getEmail())){
            throw new UserNameAlreadyExistException(userRegisterParameter.getUserName());
        }

        User user = new User();
        user.setEmail(userRegisterParameter.getEmail());
        user.setFirstName(userRegisterParameter.getFirstName());
        user.setLastName(userRegisterParameter.getLastName());
        user.setUsername(userRegisterParameter.getUserName());
        
        this.userRepository.save(user);

        return null;
    }
    
}
