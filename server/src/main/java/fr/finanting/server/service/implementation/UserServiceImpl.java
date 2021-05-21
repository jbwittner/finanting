package fr.finanting.server.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.finanting.server.codegen.model.PasswordUpdateParameter;
import fr.finanting.server.codegen.model.UserDTO;
import fr.finanting.server.codegen.model.UserRegistrationParameter;
import fr.finanting.server.codegen.model.UserUpdateParameter;
import fr.finanting.server.dto.UserDTOBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.finanting.server.exception.BadPasswordException;
import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final UserDTOBuilder USER_DTO_BUILDER = new UserDTOBuilder();

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO registerNewAccount(final UserRegistrationParameter userRegistrationParameter)  {

        if (this.userRepository.existsByEmail(userRegistrationParameter.getEmail())) {
            throw new UserEmailAlreadyExistException(userRegistrationParameter.getEmail());
        } else if (this.userRepository.existsByUserName(userRegistrationParameter.getUserName())) {
            throw new UserNameAlreadyExistException(userRegistrationParameter.getUserName());
        }

        final User user = new User();
        user.setEmail(userRegistrationParameter.getEmail());

        final String firstName = StringUtils.capitalize(userRegistrationParameter.getFirstName().toLowerCase());
        user.setFirstName(firstName);

        user.setLastName(userRegistrationParameter.getLastName().toUpperCase());
        user.setUserName(userRegistrationParameter.getUserName().toLowerCase());

        user.setPassword(this.passwordEncoder.encode(userRegistrationParameter.getPassword()));

        final List<Role> roles = new ArrayList<>();
        final List<User> users = this.userRepository.findAll();

        if(users.isEmpty()){
            roles.add(Role.ADMIN);
        }

        roles.add(Role.USER);

        user.setRoles(roles);

        this.userRepository.save(user);

        return USER_DTO_BUILDER.transform(user);
    }

    @Override
    public UserDTO getAccountInformation(final String userName) {

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        return USER_DTO_BUILDER.transform(user);
    }

    @Override
    public UserDTO updateAccountInformation(final UserUpdateParameter userUpdateParameter, final String userName) {
        
        final User user = this.userRepository.findByUserName(userName).orElseThrow();
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

        return USER_DTO_BUILDER.transform(user);
    }

    @Override
    public void updatePassword(final PasswordUpdateParameter passwordUpdateParameter, final String userName) {
        
        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        
        if(!this.passwordEncoder.matches(passwordUpdateParameter.getPreviousPassword(), user.getPassword())){
            throw new BadPasswordException();            
        }

        user.setPassword(this.passwordEncoder.encode(passwordUpdateParameter.getNewPassword()));
        this.userRepository.save(user);
        
    }
    
}
