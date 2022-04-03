package com.epam.esm.serviceimpl;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.Order;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.service.OrderService;
import com.epam.esm.utils.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public OrderDTO findById(Long id) {
        return ObjectMapperUtils
                .map(orderDAO.findById(id).orElseThrow(()-> new EntityNotFoundException("Order " + id)), OrderDTO.class);
    }

    @Override
    public PaginationAndSort<OrderDTO> find(PaginationAndSort<Order> paginationAndSort) {
        PaginationAndSort<Order> paginationAndSortFromDb = orderDAO.find(paginationAndSort);
        PaginationAndSort<OrderDTO> result = ObjectMapperUtils.convert(paginationAndSortFromDb);
        List<OrderDTO> resultList = ObjectMapperUtils.mapAll(paginationAndSort.getResultList(),OrderDTO.class);

        result.setResultList(resultList);
        return result;
    }

    @Override
    public PaginationAndSort<OrderDTO> findOrderByUser(PaginationAndSort<Order> paginationAndSort) {
        PaginationAndSort<Order> paginationAndSortFromDb = orderDAO.findOrdersByUserId(paginationAndSort);
        if (paginationAndSortFromDb.getResultList().isEmpty()) throw new EntityNotFoundException(paginationAndSort.getFindBy());
        PaginationAndSort<OrderDTO> result = ObjectMapperUtils.convert(paginationAndSortFromDb);
        List<OrderDTO> resultList = ObjectMapperUtils.mapAll(paginationAndSort.getResultList(),OrderDTO.class);

        result.setResultList(resultList);

        return result;
    }
}
