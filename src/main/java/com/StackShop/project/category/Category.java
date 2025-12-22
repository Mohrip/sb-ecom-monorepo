package com.StackShop.project.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "CATEGORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    //These annotations ensure that the categoryName field is not null or blank when creating or updating a category.
    // This helps maintain data integrity and prevents invalid entries in the database.
    // These are similar to that model in nestjs
    @NonNull
    @NotBlank
    private String categoryName;

}
