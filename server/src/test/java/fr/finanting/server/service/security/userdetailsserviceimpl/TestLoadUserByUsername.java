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

/**
 * Test class to test userDetailsServiceImpl method
 */
public class TestLoadUserByUsername extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDetailsServiceImpl userdetDetailsServiceImpl;

    private User user;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.userdetDetailsServiceImpl = new UserDetailsServiceImpl(this.userRepository);

        this.user = new User();
        final Name name = this.factory.getUniqueRandomName();
        this.user.setUserName(name.username());
        this.user.setFirstName(name.firstName());
        this.user.setLastName(name.lastName());
        this.user.setPassword(this.passwordEncoder.encode(this.factory.getRandomAlphanumericString()));
        this.user.setEmail(this.factory.getUniqueRandomEmail());

        final List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        this.user.setRoles(roles);

        this.userRepository.save(this.user);
        
    }

    /**
     * Try to load sucessfully
     */
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

    /**
     * Try to load without user
     */
    @Test
    public void testLoadFailed() throws UsernameNotFoundException {
        final String randomUserName = this.factory.getUniqueRandomAlphanumericString();

        Assertions.assertThrows(UsernameNotFoundException.class,
            () -> this.userdetDetailsServiceImpl.loadUserByUsername(randomUserName));
        
    }
    
}
