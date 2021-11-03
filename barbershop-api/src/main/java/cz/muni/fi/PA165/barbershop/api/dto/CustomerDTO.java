package cz.muni.fi.PA165.barbershop.api.dto;

import cz.muni.fi.PA165.barbershop.persistence.entity.Role;

import java.util.HashSet;
import java.util.Set;

/**
 * CustomerDTO
 *
 * @author Jan Sladký
 */
public class CustomerDTO extends PersonDTO{
    private Role role = Role.CUSTOMER;
}
