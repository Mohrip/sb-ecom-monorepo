package com.StackShop.project.Category;

public class CategoryModel {
    private Long CategoryId;
    private String CategoryName;

    public CategoryModel(String categoryName, Long categoryId) {
        this.CategoryName = categoryName;
        this.CategoryId = categoryId;
    }

    public Long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Long categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
