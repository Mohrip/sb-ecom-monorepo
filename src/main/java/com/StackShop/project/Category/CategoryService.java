package com.StackShop.project.Category;

import com.StackShop.project.Category.CategoryModel;


// Interfaces instead class to support modularity and decoupling
// This interface can be implemented by a class to provide actual service logic
public interface CategoryService {
   // long CATEGORY_ID_NOT_FOUND = -1L;
   // List<CategoryModel> getAllCategories();
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);
   // Void createCategory(CategoryModel categoryModel);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    //String deleteCategory(Long categoryId);
    CategoryDTO deleteCategory(Long categoryId);
    //String updateCategory(Long categoryId, CategoryModel categoryModel);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

}
