package com.StackShop.project.Category;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {}

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize);
        Page<CategoryModel> categoryPage = categoryRepository.findAll(pageDetails);

         //List<CategoryModel> categories = categoryRepository.findAll();-
        List<CategoryModel> categories = categoryPage.getContent();
         if(categories.isEmpty()) {
             throw new ResponseStatusException(
                     org.springframework.http.HttpStatus.NOT_FOUND, "No categories found");
         }
            List<CategoryDTO> categoriesDTOs = categories.stream()
                    .map(categoryModel -> modelMapper.map(categoryModel, CategoryDTO.class))
                    .toList();

         CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setContent(categoriesDTOs);
            categoryResponse.setPageNumber(categoryPage.getNumber());
            categoryResponse.setPageSize(categoryPage.getSize());
            categoryResponse.setTotalElements(categoryPage.getTotalElements());
            categoryResponse.setTotalPages(categoryPage.getTotalPages());
            categoryResponse.setLastPage(categoryPage.isLast());
            return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryModel categoryModel = modelMapper.map(categoryDTO, CategoryModel.class);
        CategoryModel categoryInDb = categoryRepository.findByCategoryName(categoryModel.getCategoryName());
        if (categoryInDb != null) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT, "Category already exists"
            );
        }
        CategoryModel savedCategory = categoryRepository.save(categoryModel);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }

    @Override

    public CategoryDTO deleteCategory(Long categoryId) {
        CategoryModel categoryModel = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Category not found"
                ));

        categoryRepository.delete(categoryModel);
        CategoryDTO deletedCategoryDTO = modelMapper.map(categoryModel, CategoryDTO.class);
        return deletedCategoryDTO;
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


}