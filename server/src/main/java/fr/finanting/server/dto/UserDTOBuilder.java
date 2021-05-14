package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.UserDTO;
import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTOBuilder extends Transformer<User, UserDTO> {

    @Override
    public UserDTO transform(User input){
        UserDTO userDTO = new UserDTO();

        userDTO.setUserName(input.getUserName());
        userDTO.setEmail(input.getEmail());
        userDTO.setFirstName(input.getUserName());
        userDTO.setLastName(input.getLastName());

        List<UserDTO.RolesEnum> rolesEnumList = new ArrayList<>();

        for(Role role : input.getRoles()){
            UserDTO.RolesEnum rolesEnum = UserDTO.RolesEnum.fromValue(role.toString());
            rolesEnumList.add(rolesEnum);
        }

        userDTO.setRoles(rolesEnumList);

        return userDTO;

    }

}
