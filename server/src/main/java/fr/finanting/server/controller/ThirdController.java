package fr.finanting.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.exception.BadAssociationThirdException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.ThirdNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.parameter.CreateThirdParameter;
import fr.finanting.server.parameter.UpdateThirdParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.ThirdService;

@RestController
@RequestMapping("third")
public class ThirdController {

    private ThirdService thirdService;

    @Autowired
    public ThirdController(final ThirdService thirdService){
        this.thirdService = thirdService;
    }

    @PostMapping("/createThird")
    public void createThird(final Authentication authentication,
                                    @RequestBody final CreateThirdParameter createThirdParameter)
            throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.thirdService.createThird(createThirdParameter, userDetailsImpl.getUsername());
    }

    @PostMapping("/updateThrid")
    public void updateThrid(final Authentication authentication,
                                    @RequestBody final UpdateThirdParameter updateThirdParameter)
            throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.thirdService.updateThrid(updateThirdParameter, userDetailsImpl.getUsername());
    }

}
