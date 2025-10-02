package com.StackShop.project.Category;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private List<CategoryModel> categories = new ArrayList<>();

    @GetMapping("/api/public/categories")
    public List<CategoryModel> getAllCategories() {

        return categories;
    }

    @PostMapping("/api/public/categories")
    public String creatCategory(@RequestBody CategoryModel categoryModel) {
        categories.add(categoryModel);
        return "Categories added successfully!";
    }
}
