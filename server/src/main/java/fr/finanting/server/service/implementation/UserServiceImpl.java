package fr.finanting.server.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO registerNewAccount(final UserRegisterParameter userRegisterParameter)
            throws UserEmailAlreadyExistException, UserNameAlreadyExistException {

        if (this.userRepository.existsByEmail(userRegisterParameter.getEmail())) {
            throw new UserEmailAlreadyExistException(userRegisterParameter.getEmail());
        } else if (this.userRepository.existsByUserName(userRegisterParameter.getUserName())) {
            throw new UserNameAlreadyExistException(userRegisterParameter.getUserName());
        }

        final User user = new User();
        user.setEmail(userRegisterParameter.getEmail());

        final String firstName = StringUtils.capitalize(userRegisterParameter.getFirstName().toLowerCase());
        user.setFirstName(firstName);

        user.setLastName(userRegisterParameter.getLastName().toUpperCase());
        user.setUserName(userRegisterParameter.getUserName().toLowerCase());

        user.setPassword(this.passwordEncoder.encode(userRegisterParameter.getPassword()));

        final List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        user.setRoles(roles);

        this.userRepository.save(user);

        final UserDTO userDTO = new UserDTO(user);

        return userDTO;
    }

    @Override
    public UserDTO getAccountInformations(final String userName) {

        final User user = this.userRepository.findByUserName(userName).get();

        final UserDTO userDTO = new UserDTO(user);

        return userDTO;
    }

    @Override
    public UserDTO updateAccountInformations(final UserUpdateParameter userUpdateParameter, final String userName) throws UserEmailAlreadyExistException, UserNameAlreadyExistException {
        
        final User user = this.userRepository.findByUserName(userName).get();
        Optional<User> optionalUserFind;

        if(!user.getEmail().equals(userUpdateParameter.getEmail())){
            optionalUserFind = this.userRepository.findByEmail(userUpdateParameter.getEmail());
            if(optionalUserFind.isPresent()){
                throw new UserEmailAlreadyExistException(userUpdateParameter.getEmail());
            }
        }

        if(!user.getUserName().equals(userUpdateParameter.getUserName())){
            optionalUserFind = this.userRepository.findByUserName(userUpdateParameter.getUserName());
            if(optionalUserFind.isPresent()){
                throw new UserNameAlreadyExistException(userUpdateParameter.getUserName());
            }
        }

        user.setEmail(userUpdateParameter.getEmail());

        final String firstName = StringUtils.capitalize(userUpdateParameter.getUserName().toLowerCase());
        user.setFirstName(firstName);

        user.setLastName(userUpdateParameter.getLastName().toUpperCase());

        final String userNameToUpdate = userUpdateParameter.getUserName().toLowerCase();
        user.setUserName(userNameToUpdate);

        this.userRepository.save(user);

        final UserDTO userDTO = new UserDTO(user);

        return userDTO;
    }

    @Override
    public void updatePassword(final PasswordUpdateParameter passwordUpdateParameter, final String userName) throws BadPasswordException {
        
        final User user = this.userRepository.findByUserName(userName).get();
        
        if(!this.passwordEncoder.matches(passwordUpdateParameter.getPreviousPassword(), user.getPassword())){
            throw new BadPasswordException();            
        }

        user.setPassword(this.passwordEncoder.encode(passwordUpdateParameter.getNewPassword()));
        this.userRepository.save(user);
        
    }
    
}
