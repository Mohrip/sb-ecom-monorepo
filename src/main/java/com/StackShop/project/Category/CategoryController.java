package com.StackShop.project.Category;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
}
