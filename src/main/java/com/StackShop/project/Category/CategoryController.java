package com.StackShop.project.Category;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;


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
    public ResponseEntity<List<CategoryModel>> getAllCategories() {
        List<CategoryModel> categories = categoryInterface.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
        //
    //return categoryInterface.getAllCategories();
       // return categories;
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<String> creatCategory(@RequestBody  CategoryModel categoryModel) {
        categoryInterface.createCategory(categoryModel);
        return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);


    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try{
        String status = categoryInterface.deleteCategory(categoryId);
       // return status;
          //  return new ResponseEntity<>(status, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(status);
    } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }
    }

}
