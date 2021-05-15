package fr.finanting.server.service.implementation;

import java.util.ArrayList;
import java.util.List;

import fr.finanting.server.codegen.model.AddUsersGroupParameter;
import fr.finanting.server.codegen.model.GroupDTO;
import fr.finanting.server.codegen.model.GroupParameter;
import fr.finanting.server.dto.GroupDTOBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.finanting.server.exception.GroupNameAlreadyExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.NotAdminGroupException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.DeleteGroupParameter;
import fr.finanting.server.parameter.RemoveUsersGroupParameter;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    private static final GroupDTOBuilder GROUP_DTO_BUILDER = new GroupDTOBuilder();

    @Autowired
    public GroupServiceImpl(final UserRepository userRepository, final GroupRepository groupRepository){
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<GroupDTO> getUserGroups(final String userName){

        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        return GROUP_DTO_BUILDER.transformAll(user.getGroups());

    }

    @Override
    public GroupDTO getGroup(final String groupName, final String userName)
            throws GroupNotExistException, UserNotInGroupException {

        final Group group = this.groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new GroupNotExistException(groupName));

        boolean isGroupMember = false;

        for(final User user : group.getUsers()){
            if (user.getUserName().equals(userName)) {
                isGroupMember = true;
                break;
            }
        }

        if(!isGroupMember){
            final User user = this.userRepository.findByUserName(userName).orElseThrow();
            throw new UserNotInGroupException(user, group);
        }

        return GROUP_DTO_BUILDER.transform(group);

    }

    @Override
    public GroupDTO createGroup(final GroupParameter groupParameter, final String userName)
        throws GroupNameAlreadyExistException, UserNotExistException {
        
        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        final boolean groupExist = this.groupRepository.existsByGroupName(groupParameter.getGroupName());

        if(groupExist){
            throw new GroupNameAlreadyExistException(groupParameter.getGroupName());
        }

        final Group group = new Group();
        group.setGroupName(groupParameter.getGroupName());
        group.setUserAdmin(user);

        final List<User> userList = new ArrayList<>();
        userList.add(user);

        if(groupParameter.getUsersName() != null){
            groupParameter.getUsersName().forEach((userNameToAdd) -> {
                final User userToAdd = this.userRepository.findByUserName(userNameToAdd)
                        .orElseThrow(() -> new UserNotExistException(userNameToAdd));

                userList.add(userToAdd);
            });
        }

        group.setUsers(userList);

        this.groupRepository.save(group);

        return GROUP_DTO_BUILDER.transform(group);
    }

    @Override
    public GroupDTO addUsersGroup(final AddUsersGroupParameter addUsersGroupParameter, final String userName)
            throws UserNotExistException, GroupNotExistException, NotAdminGroupException {
        
        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        final Group group = this.groupRepository.findByGroupName(addUsersGroupParameter.getGroupName())
            .orElseThrow(() -> new GroupNotExistException(addUsersGroupParameter.getGroupName()));

        final User userAdmin = group.getUserAdmin();

        if(!userAdmin.getId().equals(user.getId())){
            throw new NotAdminGroupException(group);
        }

        final List<User> userList = group.getUsers();

        for(final String userNameToAdd : addUsersGroupParameter.getUsersName()){
            boolean areAlreadyOnGroup = false;

            for(final User userGroup : group.getUsers()){
                if (userGroup.getUserName().equals(userNameToAdd)) {
                    areAlreadyOnGroup = true;
                    break;
                }
            }

            if(!areAlreadyOnGroup){
                final User userToAdd = this.userRepository.findByUserName(userNameToAdd)
                    .orElseThrow(() -> new UserNotExistException(userNameToAdd));
                userList.add(userToAdd);
            }
        }

        return GROUP_DTO_BUILDER.transform(group);
    }

    @Override
    public GroupDTO removeUsersGroup(final RemoveUsersGroupParameter removeUsersGroupParameter, final String userName)
        throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException {
        
        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        final Group group = this.groupRepository.findByGroupName(removeUsersGroupParameter.getGroupName())
            .orElseThrow(() -> new GroupNotExistException(removeUsersGroupParameter.getGroupName()));

            final User userAdmin = group.getUserAdmin();

        if(!userAdmin.getId().equals(user.getId())){
            throw new NotAdminGroupException(group);
        }

        final List<User> userList = group.getUsers();

        for(final String userNameToRemove : removeUsersGroupParameter.getUsersName()){
            final User userToRemove = this.userRepository.findByUserName(userNameToRemove)
                .orElseThrow(() -> new UserNotExistException(userNameToRemove));

            final boolean areInGroup = userList.remove(userToRemove);

            if(!areInGroup){
                throw new UserNotInGroupException(userToRemove, group);
            }
        }

        return GROUP_DTO_BUILDER.transform(group);
    }

    @Override
    public void deleteGroup(final Integer groupId, final String userName)
            throws GroupNotExistException, NotAdminGroupException {

        final User user = this.userRepository.findByUserName(userName).orElseThrow();
        final Group group = this.groupRepository.findById(groupId)
            .orElseThrow(() -> new GroupNotExistException(groupId));

        final User userAdmin = group.getUserAdmin();

        if(!userAdmin.getId().equals(user.getId())){
            throw new NotAdminGroupException(group);
        }

        this.groupRepository.delete(group);
    }
    
}
