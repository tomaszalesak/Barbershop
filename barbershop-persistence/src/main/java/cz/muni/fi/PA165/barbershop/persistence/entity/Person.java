package cz.muni.fi.PA165.barbershop.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Objects;

/**
 * Abstract Person entity
 *
 * @author Jan Sladký
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    private String phoneNumber;
    private String city;
    private String street;
    private String postalCode;
    private Role role;

    public Person() {
    }

    public Person(String login, String password, String firstname, String lastname, String phoneNumber, String city, String street, String postalCode) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

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
    public String toString() {
        return "Person{" +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (getLogin() != null ? !getLogin().equals(person.getLogin()) : person.getLogin() != null) return false;
        if (getPassword() != null ? !getPassword().equals(person.getPassword()) : person.getPassword() != null)
            return false;
        if (getFirstname() != null ? !getFirstname().equals(person.getFirstname()) : person.getFirstname() != null)
            return false;
        if (getLastname() != null ? !getLastname().equals(person.getLastname()) : person.getLastname() != null)
            return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(person.getPhoneNumber()) : person.getPhoneNumber() != null)
            return false;
        if (getCity() != null ? !getCity().equals(person.getCity()) : person.getCity() != null) return false;
        if (getStreet() != null ? !getStreet().equals(person.getStreet()) : person.getStreet() != null) return false;
        return getPostalCode() != null ? getPostalCode().equals(person.getPostalCode()) : person.getPostalCode() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), getFirstname(), getLastname(), getPhoneNumber(), getCity(), getStreet(), getPostalCode());
    }

}
