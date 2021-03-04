package fr.finanting.server.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "USERS")
@Data
public class User extends AbstractPersistant implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isExpired;

    @Column(nullable = false)
    private Boolean isLocked;

    @Column(nullable = false)
    private Boolean isCredentialsExpired;

	@Column(nullable = false)
    private Boolean isEnabled;



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !this.isExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.isLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !this.isCredentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}

}
