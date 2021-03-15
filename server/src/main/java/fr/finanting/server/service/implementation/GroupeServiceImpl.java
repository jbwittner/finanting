package fr.finanting.server.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.finanting.server.dto.GroupDTO;
import fr.finanting.server.exception.GroupNameAlreadyExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.NotAdminGroupException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.AddUsersGroupParameter;
import fr.finanting.server.parameter.DeleteGroupParameter;
import fr.finanting.server.parameter.GroupCreationParameter;
import fr.finanting.server.parameter.RemoveUsersGroupParameter;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.GroupService;

/**
 * Implementation of GroupService
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private UserRepository userRepository;
    private GroupRepository groupRepository;

    /**
     * Constructor
     */
    @Autowired
    public GroupServiceImpl(final UserRepository userRepository, final GroupRepository groupRepository){
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupDTO createGroup(final GroupCreationParameter groupCreationParameter, final String userName)
        throws GroupNameAlreadyExistException, UserNotExistException {
        
        final User user = this.userRepository.findByUserName(userName).get();

        final Boolean groupExist = this.groupRepository.existsByGroupName(groupCreationParameter.getGroupName());

        if(groupExist){
            throw new GroupNameAlreadyExistException(groupCreationParameter.getGroupName());
        }

        final Group group = new Group();
        group.setGroupName(groupCreationParameter.getGroupName());
        group.setUserAdmin(user);

        final List<User> userList = new ArrayList<>();
        userList.add(user);

        for(final String userNameToAdd : groupCreationParameter.getUsersName()){
            final User userToAdd = this.userRepository.findByUserName(userNameToAdd)
                .orElseThrow(() -> new UserNotExistException(userNameToAdd));

            userList.add(userToAdd);
        }

        group.setUsers(userList);

        this.groupRepository.save(group);

        final GroupDTO groupDTO = new GroupDTO(group);

        return groupDTO;
    }

    @Override
    public GroupDTO addUsersGroup(final AddUsersGroupParameter addUsersGroupParameter, final String userName)
            throws UserNotExistException, GroupNotExistException, NotAdminGroupException {
        
        final User user = this.userRepository.findByUserName(userName).get();
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
                if(userGroup.getUserName().equals(userNameToAdd)){
                    areAlreadyOnGroup = true;
                }
            }

            if(!areAlreadyOnGroup){
                final User userToAdd = this.userRepository.findByUserName(userNameToAdd)
                    .orElseThrow(() -> new UserNotExistException(userNameToAdd));
                userList.add(userToAdd);
            }
        }

        final GroupDTO groupDTO = new GroupDTO(group);

        return groupDTO;
    }

    @Override
    public GroupDTO removeUsersGroup(final RemoveUsersGroupParameter removeUsersGroupParameter, final String userName)
        throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException {
        
        final User user = this.userRepository.findByUserName(userName).get();
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

        final GroupDTO groupDTO = new GroupDTO(group);

        return groupDTO;
    }

    @Override
    public void deleteGroup(final DeleteGroupParameter deleteGroupParameter, final String userName)
            throws GroupNotExistException, NotAdminGroupException {

        final User user = this.userRepository.findByUserName(userName).get();
        final Group group = this.groupRepository.findByGroupName(deleteGroupParameter.getGroupName())
            .orElseThrow(() -> new GroupNotExistException(deleteGroupParameter.getGroupName()));

        final User userAdmin = group.getUserAdmin();

        if(!userAdmin.getId().equals(user.getId())){
            throw new NotAdminGroupException(group);
        }

        this.groupRepository.delete(group);
    }
    
}
