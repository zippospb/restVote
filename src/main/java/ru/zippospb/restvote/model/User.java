package ru.zippospb.restvote.model;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email", name = "user_unique_email_idx")) //добавить unicueConstraints
public class User extends AbstractNamedEntity {
    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Vote> votes;

    public User() {}

    public User(User user){
        this.id = user.id;
        this.name = user.name;
        this.email = user.email;
        this.password = user.password;
        this.roles = user.roles;
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.roles = EnumSet.of(role, roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                "} ";
    }
}
