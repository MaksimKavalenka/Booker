package by.training.dto;

import static by.training.constants.MessageConstants.LOGIN_FIELD_SIZE_MESSAGE;
import static by.training.constants.MessageConstants.PASSWORD_FIELD_SIZE_MESSAGE;
import static by.training.constants.MessageConstants.VALIDATION_MESSAGE;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import by.training.validation.constraints.PasswordsMatch;

@PasswordsMatch
public class RegisterDTO implements Serializable {

    private static final long serialVersionUID = -6999610681098603961L;

    @NotNull(message = VALIDATION_MESSAGE)
    @Size(max = 255, message = LOGIN_FIELD_SIZE_MESSAGE)
    private String            login;

    @NotNull(message = VALIDATION_MESSAGE)
    @Size(min = 3, max = 255, message = PASSWORD_FIELD_SIZE_MESSAGE)
    private String            password;

    @NotNull(message = VALIDATION_MESSAGE)
    @Size(min = 3, max = 255, message = PASSWORD_FIELD_SIZE_MESSAGE)
    private String            confirmPassword;

    public RegisterDTO() {
    }

    public RegisterDTO(String login, String password, String confirmPassword) {
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = (prime * result) + ((login == null) ? 0 : login.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RegisterDTO other = (RegisterDTO) obj;
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[login:" + login + "]";
    }

}
