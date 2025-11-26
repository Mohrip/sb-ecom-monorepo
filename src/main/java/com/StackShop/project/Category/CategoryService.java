package com.StackShop.project.Category;


// Interfaces instead class to support modularity and decoupling
// This interface can be implemented by a class to provide actual service logic
public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

}
