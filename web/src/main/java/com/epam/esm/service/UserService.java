package com.epam.esm.service;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.UserDto;
import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.pagination_and_sort.PaginationAndSort;

public interface UserService {

    UserDto findById(Long id);

    PaginationAndSort<UserDto> find(PaginationAndSort<User> paginationAndSort);

    OrderDTO buyCertificate(Long certId, Long userId);

    PaginationAndSort<UserDto> findByName(PaginationAndSort<User> paginationAndSort);

}
