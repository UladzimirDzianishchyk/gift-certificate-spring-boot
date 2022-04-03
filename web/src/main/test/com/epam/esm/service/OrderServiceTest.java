package com.epam.esm.service;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.Order;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.serviceimpl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceTest {

    OrderDAO orderDAOMoc = Mockito.mock(OrderDAO.class);
    PaginationAndSort<Order> paginationAndSort = new PaginationAndSort<>();
    PaginationAndSort<OrderDTO> paginationAndSortDto = new PaginationAndSort<>();
    Order order = new Order();
    OrderDTO orderDTO = new OrderDTO();
    List<Order> orders = new ArrayList<>();
    List<OrderDTO> orderDTOS = new ArrayList<>();


    OrderService orderService = new OrderServiceImpl(orderDAOMoc);

    @BeforeEach
    void beforeClass() {
        orders.add(order);
        orderDTOS.add(orderDTO);
        paginationAndSort.setResultList(orders);
        paginationAndSortDto.setResultList(orderDTOS);

    }


    @Test
    void findById() {
        order.setId(1L);
        orderDTO.setId(1L);
        Mockito.when(orderDAOMoc.findById(1L)).thenReturn(Optional.of(order));
        assertEquals(orderDTO.getId(),orderService.findById(1L).getId());
        assertThrows(EntityNotFoundException.class,() ->orderService.findById(5L));
    }

    @Test
    void find() {
        Mockito.when(orderDAOMoc.find(paginationAndSort)).thenReturn(paginationAndSort);
        assertEquals(paginationAndSortDto,orderService.find(paginationAndSort));
    }

    @Test
    void findOrderByUser(){
        Mockito.when(orderDAOMoc.findOrdersByUserId(paginationAndSort)).thenReturn(paginationAndSort);
        assertEquals(paginationAndSortDto,orderService.findOrderByUser(paginationAndSort));
        paginationAndSort.getResultList().clear();
        assertThrows(EntityNotFoundException.class,() -> orderService.findOrderByUser(paginationAndSort));
    }
}