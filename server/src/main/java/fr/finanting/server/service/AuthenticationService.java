package fr.finanting.server.service;

import fr.finanting.server.generated.model.LoginDTO;
import fr.finanting.server.generated.model.LoginParameter;

public interface AuthenticationService {

    LoginDTO login(LoginParameter loginParameter);
    
}
