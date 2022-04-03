package com.epam.esm.daoImpl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class GiftCertificateDAOImpl {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ModelMapper modelMapper;


//    @Override
    public Optional<GiftCertificate> save(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return Optional.ofNullable(giftCertificate);
    }

    @Transactional
//    @Override
    public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
        GiftCertificate dbCert = entityManager.find(GiftCertificate.class, giftCertificate.getId());
        if (dbCert == null) return Optional.ofNullable(dbCert);
        Set<Tag> tagsDb = dbCert.getTags();
        giftCertificate.getTags().addAll(tagsDb);
        modelMapper.map(giftCertificate, dbCert);
        dbCert.setLastUpdateDate(LocalDateTime.now());
        return Optional.ofNullable(entityManager
                .merge(dbCert));

    }


    @Transactional
//    @Override
    public Optional<GiftCertificate> updatePrice(Long id, Double price) {
        GiftCertificate giftCertificate = findById(id).orElseThrow(() -> new EntityNotFoundException("Certificate" + id));
        giftCertificate.setPrice(price);
        return Optional.ofNullable(entityManager.merge(giftCertificate));
    }

//    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }


    @SuppressWarnings("unchecked")
//    @Override
    public Optional<GiftCertificate> findOneByName(String name){
        GiftCertificate certificate;

        try {
            certificate = (GiftCertificate) entityManager
                    .createQuery("select gc from GiftCertificate gc where lower(gc.name) like lower(:name)")
                    .setParameter("name", "%" + name + "%")
                    .getSingleResult();
        }
        catch (NoResultException e){
            certificate = null;
        }
        return Optional.ofNullable(certificate);
    }


    @SuppressWarnings("unchecked")
    @Transactional
//    @Override
    public PaginationAndSort<GiftCertificate> findByName(PaginationAndSort<GiftCertificate> paginationAndSort) {
        long count = (long) entityManager
                .createQuery("Select count(gc) from GiftCertificate gc where lower(gc.name) like lower(:name)")
                .setParameter("name", "%" + paginationAndSort.getFindBy() + "%")
                .getSingleResult();
        int lastPageNumber = (int) ((count / paginationAndSort.getMaxResult()) + 1);
        paginationAndSort.setTotalPage(lastPageNumber);

        List<GiftCertificate> resultList = entityManager
                .createQuery("select gc from GiftCertificate gc where lower(gc.name) like lower(:name) order by :sort")
                .setParameter("name", "%" + paginationAndSort.getFindBy() + "%")
                .setParameter("sort", paginationAndSort.getSort())
                .setFirstResult(paginationAndSort.getCurrentPage() * paginationAndSort.getMaxResult() - paginationAndSort.getMaxResult())
                .setMaxResults(paginationAndSort.getMaxResult())
                .getResultList();

        paginationAndSort.setResultList(resultList);
        return paginationAndSort;
    }


    @SuppressWarnings("unchecked")
    @Transactional
//    @Override
    public PaginationAndSort<GiftCertificate> findByDescription(PaginationAndSort<GiftCertificate> paginationAndSort) {
        long count = (long) entityManager
                .createQuery("Select count(gc) from GiftCertificate gc where lower(gc.description) like lower(:description)")
                .setParameter("description", "%" + paginationAndSort.getFindBy() + "%")
                .getSingleResult();

        int lastPageNumber = (int) ((count / paginationAndSort.getMaxResult()) + 1);
        paginationAndSort.setTotalPage(lastPageNumber);

        List<GiftCertificate> resultList = entityManager
                .createQuery("select gc from GiftCertificate gc where lower(description) like lower(:description) order by :sortBy")
                .setParameter("description", "%" + paginationAndSort.getFindBy() + "%")
                .setParameter("sortBy", paginationAndSort.getSort())
                .setFirstResult(paginationAndSort.getCurrentPage() * paginationAndSort.getMaxResult() - paginationAndSort.getMaxResult())
                .setMaxResults(paginationAndSort.getMaxResult())
                .getResultList();

        paginationAndSort.setResultList(resultList);
        return paginationAndSort;
    }


    @SuppressWarnings("unchecked")
    @Transactional
//    @Override
    public PaginationAndSort<GiftCertificate> find(PaginationAndSort<GiftCertificate> paginationAndSort) {

        long count = (long) entityManager
                .createQuery("Select count(gc) from GiftCertificate gc")
                .getSingleResult();

        int lastPageNumber = (int) ((count / paginationAndSort.getMaxResult()) + 1);
        paginationAndSort.setTotalPage(lastPageNumber);

        List<GiftCertificate> resultList = entityManager
                .createQuery("select gc from GiftCertificate gc ORDER BY gc.id")
                .setFirstResult(paginationAndSort.getCurrentPage() * paginationAndSort.getMaxResult() - paginationAndSort.getMaxResult())
                .setMaxResults(paginationAndSort.getMaxResult())
                .getResultList();
        paginationAndSort.setResultList(resultList);
        return paginationAndSort;
    }


    @Transactional
//    @Override
    public String delete(Long id) {
        GiftCertificate giftCertificate = findById(id).orElseThrow(() -> new EntityNotFoundException("Certificate" + id));
        entityManager.remove(giftCertificate);

        return "Certificate id " + id + " deleted";
    }
}
