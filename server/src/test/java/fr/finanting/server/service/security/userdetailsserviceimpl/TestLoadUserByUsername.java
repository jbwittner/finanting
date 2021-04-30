package fr.finanting.server.service.security.userdetailsserviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.javafaker.Name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.security.UserDetailsServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestLoadUserByUsername extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    

    private UserDetailsServiceImpl userdetDetailsServiceImpl;

    private User user;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.userdetDetailsServiceImpl = new UserDetailsServiceImpl(this.userRepository);

        this.user = this.testFactory.getUser();
        
    }

    @Test
    public void testLoadSucessful() throws UsernameNotFoundException {
        final UserDetails userDetails = this.userdetDetailsServiceImpl.loadUserByUsername(this.user.getUserName());
        
        Assertions.assertTrue(userDetails.isAccountNonExpired());
        Assertions.assertTrue(userDetails.isAccountNonLocked());
        Assertions.assertTrue(userDetails.isCredentialsNonExpired());
        Assertions.assertTrue(userDetails.isEnabled());

        final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        for(final GrantedAuthority grantedAuthority : authorities){
            Assertions.assertTrue(grantedAuthority.getAuthority().equals(Role.USER.toString()));
        }

        Assertions.assertEquals(this.user.getUserName(), userDetails.getUsername());
        Assertions.assertEquals(this.user.getPassword(), userDetails.getPassword());
        
    }

    @Test
    public void testLoadFailed() throws UsernameNotFoundException {
        final String randomUserName = this.testFactory.getUniqueRandomAlphanumericString();

        Assertions.assertThrows(UsernameNotFoundException.class,
            () -> this.userdetDetailsServiceImpl.loadUserByUsername(randomUserName));
        
    }
    
}
