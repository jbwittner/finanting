package fr.finanting.server.service.implementation;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO registerNewAccount(UserRegisterParameter userRegisterParameter)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        
        if(this.userRepository.existsByEmail(userRegisterParameter.getEmail())){
            throw new UserEmailAlreadyExistException(userRegisterParameter.getEmail());
        } else if (this.userRepository.existsByUserName(userRegisterParameter.getUserName())){
            throw new UserNameAlreadyExistException(userRegisterParameter.getUserName());
        }

        User user = new User();
        user.setEmail(userRegisterParameter.getEmail());
        user.setFirstName(userRegisterParameter.getFirstName());
        user.setLastName(userRegisterParameter.getLastName());
        user.setUserName(userRegisterParameter.getUserName());
        user.setPassword(this.passwordEncoder.encode(userRegisterParameter.getPassword()));
        
        this.userRepository.save(user);

        return null;
    }
    
}
