package com.epam.esm.service;

import com.epam.esm.DTO.TagDto;
import com.epam.esm.Tag;
import com.epam.esm.pagination_and_sort.PaginationAndSort;

import java.util.Optional;

public interface TagService {

    TagDto newTag(Tag tag);

    TagDto findById(Long id);

    Tag findByName(String name);

    PaginationAndSort<TagDto> find(PaginationAndSort<Tag> paginationAndSort);

    String deleteById(Long id);

    TagDto findMostPopularEndExpensiveTagByUserId(Long userId);

}
