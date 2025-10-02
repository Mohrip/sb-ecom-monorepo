package com.StackShop.project.Category;

import jdk.jfr.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryInterface {
    private List<CategoryModel> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<CategoryModel> getAllCategories() {
        return categories;
    }

    @Override
    public Void createCategory(CategoryModel categoryModel) {
        categoryModel.setCategoryId(nextId++);
        categories.add(categoryModel);

        return null;
    }

    @Override
    public String deleteCategory(Long categoryId) {
        CategoryModel categoryModel = categories.stream()
                .filter(cat -> cat.getCategoryId().equals(categoryId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Category not found"
                ));

        categories.remove(categoryModel);
               // .orElse(null);
        return "Category with categoryId" + categoryId + "deleted successfully!";

    }
}