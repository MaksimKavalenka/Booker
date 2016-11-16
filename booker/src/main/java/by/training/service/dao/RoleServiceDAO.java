package by.training.service.dao;

import by.training.entity.RoleEntity;

public interface RoleServiceDAO {

    RoleEntity createRole(RoleEntity role);

    RoleEntity getRoleById(long id);

    RoleEntity getRoleByName(String name);

}
