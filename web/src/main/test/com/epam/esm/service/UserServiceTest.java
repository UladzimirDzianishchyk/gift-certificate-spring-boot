package com.epam.esm.service;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.UserDto;
import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.serviceimpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserDAO userDAOMoc = Mockito.mock(UserDAO.class);
    OrderDAO orderDAOMoc = Mockito.mock(OrderDAO.class);
    GiftCertificateDAO giftCertificateDAOMoc = Mockito.mock(GiftCertificateDAO.class);

    Order order = new Order();
    User user = new User();
    OrderDTO orderDTO = new OrderDTO();
    UserDto userDto = new UserDto();
    List<User> users = new ArrayList<>();
    GiftCertificate giftCertificate = new GiftCertificate();
    PaginationAndSort<User> paginationAndSort = new PaginationAndSort<>();
    PaginationAndSort<UserDto> paginationAndSortDto = new PaginationAndSort<>();



    UserService userService = new UserServiceImpl(userDAOMoc,orderDAOMoc,giftCertificateDAOMoc);

    @Test
    void findById() {
        user.setId(1L);
        userDto.setId(1L);
        Mockito.when(userDAOMoc.findById(1L)).thenReturn(Optional.of(user));
        assertEquals(userDto,userService.findById(1L));
        assertThrows(EntityNotFoundException.class,() -> userService.findById(5L));
    }

    @Test
    void find() {
        users.add(user);
        paginationAndSort.setResultList(users);
        Mockito.when(userDAOMoc.find(paginationAndSort)).thenReturn(paginationAndSort);
        assertEquals(paginationAndSortDto,userService.find(paginationAndSort));
        paginationAndSort.getResultList().clear();
        assertThrows(EntityNotFoundException.class,() -> userService.find(paginationAndSort));
    }

    @Test
    void buyCertificate() {
        user.setId(1L);
        giftCertificate.setId(1L);
        Mockito.when(giftCertificateDAOMoc.findById(1L)).thenReturn(Optional.of(giftCertificate));
        Mockito.when(userDAOMoc.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(orderDAOMoc.newOrder(giftCertificate,user)).thenReturn(Optional.of(order));

        assertEquals(orderDTO.getId(),userService.buyCertificate(1L,1L).getId());
        assertThrows(EntityNotFoundException.class,() -> userService.buyCertificate(5L,5L));
    }
}