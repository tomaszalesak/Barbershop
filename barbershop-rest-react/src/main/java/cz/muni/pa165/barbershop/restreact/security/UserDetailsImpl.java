package cz.muni.pa165.barbershop.restreact.security;

import cz.muni.fi.PA165.barbershop.api.dto.PersonDTO;
import cz.muni.fi.PA165.barbershop.persistence.entity.Customer;
import cz.muni.fi.PA165.barbershop.persistence.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private PersonDTO personDTO;

    private Class personClass;

    public UserDetailsImpl(PersonDTO personDTO, Class personClass) {
        this.personDTO = personDTO;
        this.personClass = personClass;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(getRole()));
        return authorities;
    }

    public Long getId() {
        return personDTO.getId();
    }

    public String getLogin(){
        return personDTO.getLogin();
    }

    @Override
    public String getPassword() {
        return personDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return personDTO.getLogin();
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

    public String getRole() {
        if (personDTO.getLogin().equals("Admin")) {
            return "ROLE_"+Role.ADMIN.toString();
        } else {
            if(personClass == Customer.class){
                return "ROLE_"+Role.CUSTOMER.toString();
            }else {
                return "ROLE_"+Role.EMPLOYEE.toString();
            }
        }
    }
}
