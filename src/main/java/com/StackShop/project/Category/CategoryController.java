package com.StackShop.project.Category;


import com.StackShop.project.Config.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


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
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize)
     {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);

    }

    @PostMapping("/api/public/categories")
    //Valid here to return 400 bad request if the request body is invalid
    public ResponseEntity<CategoryDTO> creatCategory(@Valid @RequestBody  CategoryDTO categoryDTO) {
       CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
//    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
//        try{
//        String status = categoryInterface.deleteCategory(categoryId);
//            return ResponseEntity.status(HttpStatus.OK).body(status);
//    } catch (ResponseStatusException e) {
//            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
//    }
//    }
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        try {
            CategoryDTO deletedCategory = categoryInterface.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        }
    }

    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO updatedCategory = categoryInterface.updateCategory(categoryId, categoryDTO);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        }
    }


}
