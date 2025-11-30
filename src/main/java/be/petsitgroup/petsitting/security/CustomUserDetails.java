package be.petsitgroup.petsitting.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.model.Role;

public class CustomUserDetails implements UserDetails {

    private final Owner owner;

    public CustomUserDetails(Owner owner) {
        this.owner = owner;
    }

    // === Spring Security stuff ===

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = owner.getRole();
        String authority = "ROLE_" + role.name(); // e.g. ROLE_OWNER, ROLE_PETSITTER
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getPassword() {
        return owner.getPassword();
    }

    @Override
    public String getUsername() {
        return owner.getEmail(); // we log in with email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // === extra helpers if you need them ===

    public Long getId() {
        return owner.getId();
    }

    public Owner getOwner() {
        return owner;
    }
}
