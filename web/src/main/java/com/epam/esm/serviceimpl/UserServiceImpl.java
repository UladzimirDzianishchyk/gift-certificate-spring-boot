package com.epam.esm.serviceimpl;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.UserDto;
import com.epam.esm.GiftCertificate;
import com.epam.esm.User;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.service.UserService;
import com.epam.esm.utils.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final OrderDAO orderDAO;
    private final GiftCertificateDAO certificateDAO;

    public UserServiceImpl(UserDAO userDAO, OrderDAO orderDAO, GiftCertificateDAO certificateDAO) {
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
        this.certificateDAO = certificateDAO;
    }

    @Transactional
    @Override
    public UserDto findById(Long id) {
        return ObjectMapperUtils.map(userDAO
                .findById(id).orElseThrow(()->new EntityNotFoundException("User " + id)),UserDto.class);
    }

    @Override
    public PaginationAndSort<UserDto> find(PaginationAndSort<User> paginationAndSort) {
        PaginationAndSort<User> paginationAndSortFromDb = userDAO.find(paginationAndSort);
        return mapOrEntityNotFound(paginationAndSort, paginationAndSortFromDb);
    }

    private PaginationAndSort<UserDto> mapOrEntityNotFound(PaginationAndSort<User> paginationAndSort, PaginationAndSort<User> paginationAndSortFromDb) {
        if (paginationAndSortFromDb.getResultList().isEmpty()) throw new EntityNotFoundException(paginationAndSort.getFindBy());
        PaginationAndSort<UserDto> result = ObjectMapperUtils.convert(paginationAndSortFromDb);
        List<UserDto> resultList = ObjectMapperUtils.mapAll(paginationAndSort.getResultList(),UserDto.class);

        result.setResultList(resultList);
        return result;
    }

    @Transactional
    @Override
    public OrderDTO buyCertificate(Long certId, Long userId) {
        GiftCertificate certificate = certificateDAO.findById(certId)
                .orElseThrow(()->new EntityNotFoundException("Certificate " + certId));
        User user = userDAO.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User " + userId));

        return ObjectMapperUtils
                .map(orderDAO.newOrder(certificate, user)
                        .orElseThrow(()-> new EntityNotFoundException("Order not found")),OrderDTO.class);
    }

    @Override
    public PaginationAndSort<UserDto> findByName(PaginationAndSort<User> paginationAndSort) {
        PaginationAndSort<User> paginationAndSortFromDb = userDAO.findByName(paginationAndSort);
        return mapOrEntityNotFound(paginationAndSort, paginationAndSortFromDb);
    }

}
