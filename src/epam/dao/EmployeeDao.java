package epam.dao;

import epam.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDao {

    void add(Employee employee) throws SQLException;

    List<Employee> getAll() throws SQLException;

    Employee getById(Long id) throws SQLException;

    void update(Employee employee) throws SQLException;

    void remove(Employee employee) throws SQLException;

}
