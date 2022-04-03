package com.epam.esm.dao;

import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.pagination_and_sort.PaginationAndSort;

import java.util.Optional;

public interface UserDAO {

    Optional<User> findById(Long id);

    PaginationAndSort<User> find(PaginationAndSort<User> paginationAndSort);

    PaginationAndSort<User> findByName(PaginationAndSort<User> paginationAndSort);

}
