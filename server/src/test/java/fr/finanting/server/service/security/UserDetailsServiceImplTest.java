package fr.finanting.server.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.javafaker.Name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.finanting.server.model.Role;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.security.UserDetailsServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class UserDetailsServiceImplTest extends AbstractMotherIntegrationTest {

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
        Name name = this.factory.getUniqueRandomName();
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

    @Test
    public void testLoadSucessful() throws UsernameNotFoundException {
        UserDetails userDetails = this.userdetDetailsServiceImpl.loadUserByUsername(this.user.getUserName());
        
        Assertions.assertTrue(userDetails.isAccountNonExpired());
        Assertions.assertTrue(userDetails.isAccountNonLocked());
        Assertions.assertTrue(userDetails.isCredentialsNonExpired());
        Assertions.assertTrue(userDetails.isEnabled());

        final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        for(GrantedAuthority grantedAuthority : authorities){
            Assertions.assertTrue(grantedAuthority.getAuthority().equals(Role.USER.toString()));
        }

        Assertions.assertEquals(this.user.getUserName(), userDetails.getUsername());
        Assertions.assertEquals(this.user.getPassword(), userDetails.getPassword());
        
    }

    @Test
    public void testLoadFailed() throws UsernameNotFoundException {
        String randomUserName = this.factory.getUniqueRandomAlphanumericString();

        Assertions.assertThrows(UsernameNotFoundException.class,
            () -> this.userdetDetailsServiceImpl.loadUserByUsername(randomUserName));
        
    }
    
}
