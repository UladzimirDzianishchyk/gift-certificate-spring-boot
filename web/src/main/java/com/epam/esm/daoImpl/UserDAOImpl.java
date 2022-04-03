package com.epam.esm.daoImpl;

import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class,id));
    }

    @Override
    public PaginationAndSort<User> find(PaginationAndSort<User> paginationAndSort) {
        long count = (long) entityManager
                .createQuery("Select count(u) from User u")
                .getSingleResult();

        int lastPageNumber = (int) ((count / paginationAndSort.getMaxResult()) + 1);
        paginationAndSort.setTotalPage(lastPageNumber);

        @SuppressWarnings("unchecked")
        List<User> resultList = entityManager
                .createQuery("select u from User u ORDER BY u.id")
                .setFirstResult(paginationAndSort.getCurrentPage() * paginationAndSort.getMaxResult() - paginationAndSort.getMaxResult())
                .setMaxResults(paginationAndSort.getMaxResult())
                .getResultList();
        paginationAndSort.setResultList(resultList);
        return paginationAndSort;
    }

    @Transactional
    @Override
    public PaginationAndSort<User> findByName(PaginationAndSort<User> paginationAndSort) {
        long count = (long) entityManager
                .createQuery("Select count(u) from User u where lower(u.userName) like lower(:name) ")
                .setParameter("name","%" + paginationAndSort.getFindBy() + "%")
                .getSingleResult();

        int lastPageNumber = (int) ((count / paginationAndSort.getMaxResult()) + 1);
        paginationAndSort.setTotalPage(lastPageNumber);

        @SuppressWarnings("unchecked")
        List<User> resultList = entityManager
                .createQuery("select u from User u where lower(u.userName) like lower(:name) order by :sort")
                .setParameter("name", "%" + paginationAndSort.getFindBy() + "%")
                .setParameter("sort", paginationAndSort.getSort())
                .setFirstResult(paginationAndSort.getCurrentPage() * paginationAndSort.getMaxResult() - paginationAndSort.getMaxResult())
                .setMaxResults(paginationAndSort.getMaxResult())
                .getResultList();
        paginationAndSort.setResultList(resultList);
        return paginationAndSort;
    }

}
