package com.StackShop.project.Category;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;




import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private CategoryInterface categoryInterface;

    @Autowired
    public CategoryController(CategoryInterface categoryInterface) {
        this.categoryInterface = categoryInterface;
    }

    @GetMapping("/api/public/categories")
    public List<CategoryModel> getAllCategories() {
    return categoryInterface.getAllCategories();
       // return categories;
    }

    @PostMapping("/api/public/categories")
    public String creatCategory(@RequestBody  CategoryModel categoryModel) {
        categoryInterface.createCategory(categoryModel);

        return "Categories added successfully!";
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId) {
        String status = categoryInterface.deleteCategory(categoryId);
        return status;
    }

}
