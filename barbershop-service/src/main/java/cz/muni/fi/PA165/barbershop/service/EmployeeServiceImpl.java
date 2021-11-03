package cz.muni.fi.PA165.barbershop.service;

import cz.muni.fi.PA165.barbershop.persistence.dao.EmployeeDao;
import cz.muni.fi.PA165.barbershop.persistence.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl extends PersonServiceImpl<Employee, EmployeeDao> implements EmployeeService {

    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao, PasswordEncoder encoder) {
        super(employeeDao, encoder);
        this.employeeDao = employeeDao;
    }


    @Override
    public List<Employee> getAll() {
        List<Employee> employee = employeeDao.findAll();
        return employee.stream().filter(e -> !e.getLogin().equals("Admin")).collect(Collectors.toList());
    }

    @Override
    public List<Employee> findByName(String firstname, String lastname) {
        return employeeDao.findByName(firstname, lastname);
    }
}
