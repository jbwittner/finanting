package fr.finanting.server.service.implementation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.finanting.server.dto.UserDTO;
import fr.finanting.server.exception.BadPasswordException;
import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.PasswordUpdateParameter;
import fr.finanting.server.parameter.UserRegisterParameter;
import fr.finanting.server.parameter.UserUpdateParameter;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO registerNewAccount(UserRegisterParameter userRegisterParameter)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException {

        if (this.userRepository.existsByEmail(userRegisterParameter.getEmail())) {
            throw new UserEmailAlreadyExistException(userRegisterParameter.getEmail());
        } else if (this.userRepository.existsByUserName(userRegisterParameter.getUserName())) {
            throw new UserNameAlreadyExistException(userRegisterParameter.getUserName());
        }

        User user = new User();
        user.setEmail(userRegisterParameter.getEmail());
        user.setFirstName(userRegisterParameter.getFirstName());
        user.setLastName(userRegisterParameter.getLastName());
        user.setUserName(userRegisterParameter.getUserName());
        user.setPassword(this.passwordEncoder.encode(userRegisterParameter.getPassword()));

        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        roles.add(Role.ADMIN);

        user.setRoles(roles);

        user = this.userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return userDTO;
    }

    @Override
    public UserDTO getAccountInformations(String userName) {

        User user = this.userRepository.findByUserName(userName).get();

        UserDTO userDTO = new UserDTO(user);

        return userDTO;
    }

    @Override
    public UserDTO updateAccountInformations(UserUpdateParameter userUpdateParameter, String userName) {
        
        User user = this.userRepository.findByUserName(userName).get();

        user.setEmail(userUpdateParameter.getEmail());
        user.setFirstName(userUpdateParameter.getFirstName());
        user.setLastName(userUpdateParameter.getLastName());
        user.setUserName(userUpdateParameter.getUserName());

        user = this.userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return userDTO;
    }

    @Override
    public void updatePassword(PasswordUpdateParameter passwordUpdateParameter, String userName) throws BadPasswordException {
        
        User user = this.userRepository.findByUserName(userName).get();
        
        if(!this.passwordEncoder.matches(passwordUpdateParameter.getPreviousPassword(), user.getPassword())){
            throw new BadPasswordException();            
        }

        user.setPassword(this.passwordEncoder.encode(passwordUpdateParameter.getNewPassword()));
        this.userRepository.save(user);
        
    }

    
}
