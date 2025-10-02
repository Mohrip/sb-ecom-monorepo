package com.StackShop.project.Category;

import java.util.List;

// Interfaces instead class to support modularity and decoupling
// This interface can be implemented by a class to provide actual service logic
public interface CategoryInterface {
    List<CategoryModel> getAllCategories();
    Void createCategory(CategoryModel categoryModel);

    String deleteCategory(Long categoryId);
}
