package com.StackShop.project.Category;

import java.util.List;
import com.StackShop.project.Category.CategoryModel;


// Interfaces instead class to support modularity and decoupling
// This interface can be implemented by a class to provide actual service logic
public interface CategoryService {
   // List<CategoryModel> getAllCategories();
    CategoryResponse getAllCategories();
    Void createCategory(CategoryModel categoryModel);
    String deleteCategory(Long categoryId);
    String updateCategory(Long categoryId, CategoryModel categoryModel);

}
