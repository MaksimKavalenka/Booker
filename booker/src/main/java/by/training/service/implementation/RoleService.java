package by.training.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;

import by.training.entity.RoleEntity;
import by.training.jpa.repository.RoleRepository;
import by.training.service.dao.RoleServiceDAO;

public class RoleService implements RoleServiceDAO {

    @Autowired
    private RoleRepository repository;

    @Override
    public RoleEntity createRole(RoleEntity role) {
        return repository.save(role);
    }

    @Override
    public RoleEntity getRoleById(long id) {
        return repository.findOne(id);
    }

    @Override
    public RoleEntity getRoleByName(String name) {
        return repository.findByName(name);
    }

}
