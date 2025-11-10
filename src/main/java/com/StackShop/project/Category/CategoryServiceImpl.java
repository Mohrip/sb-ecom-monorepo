package com.StackShop.project.Category;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.StackShop.project.Category.CategoryService;
import com.StackShop.project.Category.CategoryModel;
import com.StackShop.project.Category.CategoryRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
   // private List<CategoryModel> categories = new ArrayList<>();
  //  private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {}

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
         List<CategoryModel> categories = categoryRepository.findAll();
         if(categories.isEmpty()) {
             throw new ResponseStatusException(
                     org.springframework.http.HttpStatus.NOT_FOUND, "No categories found");
         }
            List<CategoryDTO> categoriesDTOs = categories.stream()
                    .map(categoryModel -> modelMapper.map(categoryModel, CategoryDTO.class))
                    .toList();

         CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setContent(categoriesDTOs);
            return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryModel categoryModel = modelMapper.map(categoryDTO, CategoryModel.class);
        CategoryModel categoryInDb = categoryRepository.findByCategoryName(categoryModel.getCategoryName());
        if(categoryInDb != null) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT, "Category already exists"
            );
        }
        CategoryModel savedCategory = categoryRepository.save(categoryModel);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
       // return modelMapper.map(savedCategory, CategoryDTO.class);
       // categoryRepository.save(categoryModel);
      //  return null;
    }

    @Override
    public String deleteCategory(Long categoryId) {
        List<CategoryModel> categories = categoryRepository.findAll();
        CategoryModel categoryModel = categories.stream()
                .filter(cat -> cat.getCategoryId().equals(categoryId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Category not found"
                ));

        categoryRepository.delete(categoryModel);
        return "Category with categoryId" + categoryId + "deleted successfully!";

    }
    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        CategoryModel existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Category not found"
                ));

        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        CategoryModel updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }
//    public String updateCategory(Long categoryId, CategoryModel categoryModel) {
//        CategoryModel existingCategory = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResponseStatusException(
//                        org.springframework.http.HttpStatus.NOT_FOUND, "Category not found"
//                ));
//
//        existingCategory.setCategoryName(categoryModel.getCategoryName());
//        categoryRepository.save(existingCategory);
//        return "Category updated successfully!";
//    }

}