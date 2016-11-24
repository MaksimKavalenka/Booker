package by.training.spring.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import by.training.common.Role;
import by.training.entity.RoleEntity;
import by.training.service.dao.RoleServiceDAO;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleServiceDAO roleService;

    public DatabaseInitializer(RoleServiceDAO roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        roleInit();
    }

    private void roleInit() {
        for (Role role : Role.values()) {
            if (roleService.getRoleByName(role.name()) == null) {
                roleService.createRole(new RoleEntity(role.name()));
            }
        }
    }

}
