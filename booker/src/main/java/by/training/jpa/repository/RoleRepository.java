package by.training.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import by.training.entity.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);

}
