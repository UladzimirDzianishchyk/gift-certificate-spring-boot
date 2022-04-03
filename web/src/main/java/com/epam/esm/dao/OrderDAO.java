package com.epam.esm.dao;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.pagination_and_sort.PaginationAndSort;

import java.util.Optional;

public interface OrderDAO {

    Optional<Order> newOrder(GiftCertificate cert, User user);

    Optional<Order> findById(Long id);

    PaginationAndSort<Order> find(PaginationAndSort<Order> paginationAndSort);

    PaginationAndSort<Order> findOrdersByUserId(PaginationAndSort<Order> paginationAndSort);
}
