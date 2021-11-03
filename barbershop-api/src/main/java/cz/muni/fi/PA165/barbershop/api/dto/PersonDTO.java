package cz.muni.fi.PA165.barbershop.api.dto;

import cz.muni.fi.PA165.barbershop.persistence.entity.Role;

/**
 * abstract class PersonDTO
 *
 * @author Jan Sladký
 */
public abstract class PersonDTO {

    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String city;
    private String street;
    private String postalCode;
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDTO)) return false;

        PersonDTO personDTO = (PersonDTO) o;

        if (getLogin() != null ? !getLogin().equals(personDTO.getLogin()) : personDTO.getLogin() != null) return false;
        if (getPassword() != null ? !getPassword().equals(personDTO.getPassword()) : personDTO.getPassword() != null)
            return false;
        if (getFirstname() != null ? !getFirstname().equals(personDTO.getFirstname()) : personDTO.getFirstname() != null)
            return false;
        if (getLastname() != null ? !getLastname().equals(personDTO.getLastname()) : personDTO.getLastname() != null)
            return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(personDTO.getPhoneNumber()) : personDTO.getPhoneNumber() != null)
            return false;
        if (getCity() != null ? !getCity().equals(personDTO.getCity()) : personDTO.getCity() != null) return false;
        if (getStreet() != null ? !getStreet().equals(personDTO.getStreet()) : personDTO.getStreet() != null)
            return false;
        return getPostalCode() != null ? getPostalCode().equals(personDTO.getPostalCode()) : personDTO.getPostalCode() == null;
    }

    @Override
    public int hashCode() {
        int result = getLogin() != null ? getLogin().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getFirstname() != null ? getFirstname().hashCode() : 0);
        result = 31 * result + (getLastname() != null ? getLastname().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
        result = 31 * result + (getPostalCode() != null ? getPostalCode().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }


}
