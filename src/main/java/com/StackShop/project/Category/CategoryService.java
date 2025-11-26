package com.StackShop.project.Category;

import com.StackShop.project.Category.CategoryModel;


// Interfaces instead class to support modularity and decoupling
// This interface can be implemented by a class to provide actual service logic
public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    //String deleteCategory(Long categoryId);
    CategoryDTO deleteCategory(Long categoryId);
    //String updateCategory(Long categoryId, CategoryModel categoryModel);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

}
