package cz.muni.fi.PA165.barbershop.service;


import cz.muni.fi.PA165.barbershop.persistence.dao.PersonDao;
import cz.muni.fi.PA165.barbershop.persistence.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public abstract class  PersonServiceImpl<T extends Person, D extends PersonDao<T>> implements PersonService<T> {

    private D personDao;

    PasswordEncoder encoder;

    public PersonServiceImpl (D personDao, PasswordEncoder encoder) {
        this.personDao = personDao;
        this.encoder = encoder;
    }

    @Override
    public void register(T p, String unencryptedPassword) {
        p.setPassword(encoder.encode(unencryptedPassword));
        personDao.create(p);
    }

    @Override
    public List<T> getAll() {
        return personDao.findAll();
    }

    @Override
    public boolean authenticate(T p, String password) {
        return encoder.matches(password, p.getPassword());
    }

    @Override
    public T findById(Long personId) {
        return personDao.findById(personId);
    }

    @Override
    public T findByLogin(String personLogin) {
        return personDao.findByLogin(personLogin);
    }

    @Override
    public void update(T person) {
        personDao.update(person);
    }

    @Override
    public void delete(T person) {
        personDao.delete(person.getId());
    }


}
