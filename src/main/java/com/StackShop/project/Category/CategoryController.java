package com.StackShop.project.Category;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;
    //private final CategoryDTO categoryDTO = new ();
    // Here I'm using the interface to support loose coupling
    private CategoryService categoryInterface;

    @Autowired
    public CategoryController(CategoryService categoryInterface, CategoryService categoryService) {
        this.categoryInterface = categoryInterface;
        this.categoryService = categoryService;
       // this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories() {
        CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);

    }

    @PostMapping("/api/public/categories")
    //Valid here to return 400 bad request if the request body is invalid
    public ResponseEntity<CategoryDTO> creatCategory(@Valid @RequestBody  CategoryDTO categoryDTO) {
       CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);
       // categoryInterface.createCategory(categoryModel);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);




    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try{
        String status = categoryInterface.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(status);
    } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }
    }

    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryModel categoryModel) {
        try {
            String status = categoryInterface.updateCategory(categoryId, categoryModel);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

}
