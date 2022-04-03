package com.epam.esm.service;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.Order;
import com.epam.esm.pagination_and_sort.PaginationAndSort;

public interface OrderService {

    OrderDTO findById(Long id);

    PaginationAndSort<OrderDTO> find(PaginationAndSort<Order> paginationAndSort);

    PaginationAndSort<OrderDTO> findOrderByUser(PaginationAndSort<Order> paginationAndSort);

}
