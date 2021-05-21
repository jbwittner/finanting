package fr.finanting.server.service.security.userdetailsserviceimpl;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.security.UserDetailsServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestLoadUserByUsername extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    

    private UserDetailsServiceImpl usedDetailsServiceImpl;

    private User user;

    @Override
    protected void initDataBeforeEach() {
        this.usedDetailsServiceImpl = new UserDetailsServiceImpl(this.userRepository);

        this.user = this.testFactory.getUser();
        
    }

    @Test
    public void testLoadSuccessful() throws UsernameNotFoundException {
        final UserDetails userDetails = this.usedDetailsServiceImpl.loadUserByUsername(this.user.getUserName());
        
        Assertions.assertTrue(userDetails.isAccountNonExpired());
        Assertions.assertTrue(userDetails.isAccountNonLocked());
        Assertions.assertTrue(userDetails.isCredentialsNonExpired());
        Assertions.assertTrue(userDetails.isEnabled());

        final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        for(final GrantedAuthority grantedAuthority : authorities){
            Assertions.assertEquals(grantedAuthority.getAuthority(), Role.USER.toString());
        }
        Assertions.assertEquals(this.user.getUserName(), userDetails.getUsername());
        Assertions.assertEquals(this.user.getPassword(), userDetails.getPassword());
    }

    @Test
    public void testLoadFailed() throws UsernameNotFoundException {
        final String randomUserName = this.testFactory.getUniqueRandomAlphanumericString();

        Assertions.assertThrows(UsernameNotFoundException.class,
            () -> this.usedDetailsServiceImpl.loadUserByUsername(randomUserName));
        
    }
    
}
