package com.epam.esm.daoImpl;

import com.epam.esm.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class TagDAOImpl implements TagDAO {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Optional<Tag> save(Tag tag) {
        entityManager.persist(tag);
        return Optional.ofNullable(tag);
    }

    @Transactional
    @Override
    public String delete(Long id) {
        Tag tag = findById(id).orElseThrow(() -> new EntityNotFoundException("Tag " + id));
        entityManager.remove(tag);
        return "Tag id " + id + " deleted";
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }


    @Override
    public Optional<Tag> findOneByName(String name) {

        Tag tag;
        try {
            tag = (Tag) entityManager.createQuery("select t from Tag t where lower(t.name) like lower(:name)")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            tag = null;
        }
        return Optional.ofNullable(tag);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Tag> findByName(String name) {
        return new HashSet<Tag>(entityManager.createQuery("select t from Tag t where lower(t.name) like lower(:name)")
                .setParameter("name","%" + name + "%")
                .getResultList());
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public PaginationAndSort<Tag> find(PaginationAndSort<Tag> paginationAndSort) {
        long count = (long) entityManager
                .createQuery("Select count(t) from Tag t")
                .getSingleResult();
        int lastPageNumber = (int) ((count / paginationAndSort.getMaxResult()) + 1);
        paginationAndSort.setTotalPage(lastPageNumber);

        List<Tag> resultList = entityManager
                .createQuery("select t from Tag t ORDER BY :sort")
                .setFirstResult(paginationAndSort.getCurrentPage() * paginationAndSort.getMaxResult() - paginationAndSort.getMaxResult())
                .setMaxResults(paginationAndSort.getMaxResult())
                .setParameter("sort", paginationAndSort.getSort())
                .getResultList();
        paginationAndSort.setResultList(resultList);
        return paginationAndSort;
    }

    @Override
    public Optional<Tag> findMostPopularEndExpensiveTagByUserId(Long userId) {
        Tag tag;
        try {
            String sql = "select id, name from (select sum(o.price) as price, t.id, t.name" +
                    "         from orders as o" +
                    "                  join gift_certificate_tag as gct on gct.gift_certificate_id=o.gift_certificate_id" +
                    "                  join tag as t on gct.tag_id=t.id" +
                    "         where o.user_id=:userId" +
                    "         group by o.price, t.id" +
                    "         order by price desc limit 1) as r";
            tag = (Tag) entityManager.createNativeQuery(sql, Tag.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            tag = null;
        }
        return Optional.ofNullable(tag);
    }
}
