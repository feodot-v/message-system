package com.mycompany.messagesystem.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class MUser implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    @NotNull(message = "Введите имя")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё]+$", message = "Имя может содержать только буквы")
    private String firstname;
    @NotNull(message = "Введите фамилию")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё]+$", message = "Фамилия может содержать только буквы")
    private String lastname;
    @NotNull(message = "Введите пароль")
    @Size(min = 6, max = 20, message = "Пароль должен содержать от 6 до 20 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Пароль может содержать только буквы латинского алфавита")
    private String password;
    @NotNull(message = "Введите никнейм")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Никнейм может содержать только буквы латинского алфавита")
    private String userName;

    @Override
    public String toString() {
        return String.format("User %s with password %s has fullname %s %s",
                userName, password, lastname, firstname);
    }

    public MUser() {
    }

    public MUser(Long id, String firstname, String lastname, String password, String userName) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.userName = userName;
    }

    @Override
    public boolean equals(Object obj) {
        MUser user;
        if (obj instanceof MUser) {
            user = (MUser) obj;
        } else {
            return false;
        }
        if (Objects.equals(user.getId(), id)
                && Objects.equals(user.getFirstname(), firstname)
                && Objects.equals(user.getLastname(), lastname)
                && Objects.equals(user.getPassword(), password)
                && Objects.equals(user.getUserName(), userName)) {
            return true;
        }
        return false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
