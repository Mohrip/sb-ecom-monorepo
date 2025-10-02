package com.StackShop.project.Category;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryInterface {
    private List<CategoryModel> categories = new ArrayList<>();
    private Long  nextId = 1L;

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
}
