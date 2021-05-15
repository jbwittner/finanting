package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.GroupDTO;
import fr.finanting.server.codegen.model.UserDTO;
import fr.finanting.server.model.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupDTOBuilder extends Transformer<Group, GroupDTO>{

    private static final UserDTOBuilder USER_DTO_BUILDER = new UserDTOBuilder();

    @Override
    public GroupDTO transform(Group input) {
        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setGroupName(input.getGroupName());

        UserDTO userAdminDTO = USER_DTO_BUILDER.transform(input.getUserAdmin());
        groupDTO.setUserAdmin(userAdminDTO);

        List<UserDTO> userDTOList = USER_DTO_BUILDER.transformAll(input.getUsers());
        groupDTO.setGroupUsers(userDTOList);

        return groupDTO;
    }

    @Override
    public List<GroupDTO> transformAll(List<Group> input) {

        List<GroupDTO> groupDTOList = new ArrayList<>();

        input.forEach((inputGroup) -> groupDTOList.add(this.transform(inputGroup)));

        return groupDTOList;
    }
}
