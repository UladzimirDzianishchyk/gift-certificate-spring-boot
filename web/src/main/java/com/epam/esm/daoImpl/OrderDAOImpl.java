package com.epam.esm.daoImpl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.Status;
import com.epam.esm.User;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAOImpl implements OrderDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Order> newOrder(GiftCertificate cert, User user) {
        Order order = new Order();
        order.setCertificate(cert);
        order.setPrice(cert.getPrice());
        order.setUser(user);
        order.setStatus(Status.NEW);
        entityManager.persist(order);
        return Optional.of(order);
    }


    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class,id));
    }


    @Override
    public PaginationAndSort<Order> findOrdersByUserId(PaginationAndSort<Order> paginationAndSort) {
        Long id = null;
        try {
            id = Long.parseLong(paginationAndSort.getFindBy());
        }
        catch (NumberFormatException e){
            throw new BadRequestException(paginationAndSort.getFindBy());
        }
        long count = (long) entityManager
                .createQuery("Select count(o) from Order o where o.user.id = :id ")
                .setParameter("id",id)
                .getSingleResult();

        int lastPageNumber = (int) ((count / paginationAndSort.getMaxResult()) + 1);
        paginationAndSort.setTotalPage(lastPageNumber);

        @SuppressWarnings("unchecked")
        List<Order> resultList = entityManager
                .createQuery("select o from Order o where o.user.id = :id order by :sort")
                .setParameter("id", id)
                .setParameter("sort", paginationAndSort.getSort())
                .setFirstResult(paginationAndSort.getCurrentPage() * paginationAndSort.getMaxResult() - paginationAndSort.getMaxResult())
                .setMaxResults(paginationAndSort.getMaxResult())
                .getResultList();
        paginationAndSort.setResultList(resultList);
        System.out.println(count);
        System.out.println(resultList);
        System.out.println(paginationAndSort.getFindBy());

        return paginationAndSort;
    }


    @Override
    public PaginationAndSort<Order> find(PaginationAndSort<Order> paginationAndSort) {
        long count = (long) entityManager
                .createQuery("Select count(o) from Order o")
                .getSingleResult();

        int lastPageNumber = (int) ((count / paginationAndSort.getMaxResult()) + 1);
        paginationAndSort.setTotalPage(lastPageNumber);

        @SuppressWarnings("unchecked")
        List<Order> resultList = entityManager
                .createQuery("select o from Order o ORDER BY :sort")
                .setFirstResult(paginationAndSort.getCurrentPage() * paginationAndSort.getMaxResult() - paginationAndSort.getMaxResult())
                .setMaxResults(paginationAndSort.getMaxResult())
                .setParameter("sort",paginationAndSort.getSort())
                .getResultList();
        paginationAndSort.setResultList(resultList);
        return paginationAndSort;
    }
}
