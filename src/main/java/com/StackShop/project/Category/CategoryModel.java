package com.StackShop.project.Category;

import jakarta.persistence.*;

@Entity
@Table(name = "CATEGORY")
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;

    public CategoryModel() {
    }

    public CategoryModel(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryModel(String categoryName, Long categoryId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
