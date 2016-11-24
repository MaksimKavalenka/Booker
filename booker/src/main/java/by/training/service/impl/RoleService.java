package by.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.training.entity.RoleEntity;
import by.training.jpa.repository.RoleRepository;
import by.training.service.dao.RoleServiceDAO;

@Service("roleService")
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
