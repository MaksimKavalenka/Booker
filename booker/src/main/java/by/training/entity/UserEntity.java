package by.training.entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class UserEntity extends AbstractEntity implements UserDetails {

    private static final long     serialVersionUID = -7057504641234768928L;

    @JsonIgnore
    private boolean               accountNonExpired;

    @JsonIgnore
    private boolean               accountNonLocked;

    @JsonIgnore
    private boolean               credentialsNonExpired;

    @JsonIgnore
    private boolean               enabled;

    @Column(name = "login", unique = true, nullable = false, length = 255)
    private String                login;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String                password;

    @JsonIgnore
    @ManyToMany(targetEntity = RoleEntity.class, cascade = {
            CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, updatable = false))
    private Set<GrantedAuthority> roles;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.DETACH}, mappedBy = "uploader")
    private Set<BookStatusEntity> books;

    public UserEntity() {
        super();
    }

    public UserEntity(String login, String password, Set<GrantedAuthority> roles) {
        this(true, true, true, true, login, password, null, roles);
    }

    public UserEntity(boolean accountNonExpired, boolean accountNonLocked,
            boolean credentialsNonExpired, boolean enabled, String login, String password,
            String photo, Set<GrantedAuthority> roles) {
        super();
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",login:" + login + "]";
    }

}
