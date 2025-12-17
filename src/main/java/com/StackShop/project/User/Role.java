package com.StackShop.project.User;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false)
    private Integer roleId;


    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private UserRole roleName;

    public Role(UserRole roleName) {
        this.roleName = roleName;
    }
}
