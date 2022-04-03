package com.epam.esm.dao;

import com.epam.esm.Tag;
import com.epam.esm.pagination_and_sort.PaginationAndSort;

import java.util.Optional;
import java.util.Set;

public interface TagDAO {

    Optional<Tag> save(Tag tag);

    String delete(Long id);

    Optional<Tag> findById(Long id);

    Optional<Tag> findOneByName(String name);

    Set<Tag> findByName(String name);

    PaginationAndSort<Tag> find(PaginationAndSort<Tag> paginationAndSort);

    Optional<Tag> findMostPopularEndExpensiveTagByUserId(Long userId);
}
