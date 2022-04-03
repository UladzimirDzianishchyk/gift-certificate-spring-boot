package com.epam.esm.dao;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


//@SpringBootTest
//@ActiveProfiles("dev")
//@Sql(executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD,scripts= {"classpath:testSchema.sql","classpath:testData.sql"})
//@Sql(executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:testDrop.sql")

public class GiftCertificateDAOTest {

//    GiftCertificate giftCertificateFromDb = new GiftCertificate("GiftCertificateFromDb", "DescriptionFromDb", 45D, 2L);
//    PaginationAndSort<GiftCertificate> paginationAndSortByName = new PaginationAndSort<>(1,1,"giftcertificate1","lastUpdateDate");
//    PaginationAndSort<GiftCertificate> paginationAndSortByDescription = new PaginationAndSort<>(1,1,"descriptionone","lastUpdateDate");
//    PaginationAndSort<GiftCertificate> paginationAndSortByError = new PaginationAndSort<>(1,1,"someName","lastUpdateDate");
//    Set<Tag> tags = new HashSet<Tag>();
//
//    @Autowired
//    GiftCertificateDAO giftCertificateDAO;
//
//
//    @Test
//    void findByDescription() {
//        String desc = giftCertificateDAO
//                .findByDescription(paginationAndSortByDescription)
//                .getResultList()
//                .get(0)
//                .getDescription();
//        assertEquals("DescriptionOne",desc);
//        assertTrue(giftCertificateDAO.findByDescription(paginationAndSortByError).getResultList().isEmpty());
//    }
//
//    @Test
//    void findByName() {
//        String name = giftCertificateDAO
//                .findByName(paginationAndSortByName)
//                .getResultList()
//                .get(0)
//                .getName();
//        assertEquals("GiftCertificate1",name);
//        assertTrue(giftCertificateDAO.findByName(paginationAndSortByError).getResultList().isEmpty());
//    }
//
//
//    @Test
//    void findById() {
//        assertTrue(giftCertificateDAO.findById(1l).isPresent());
//        assertFalse(giftCertificateDAO.findById(100L).isPresent());
//    }
//
//    @Test
//    @Transactional
//    void save() {
//        assertEquals(giftCertificateFromDb.getName(), giftCertificateDAO.save(giftCertificateFromDb).get().getName());
//    }
//
//
//    @Test
//    void update() {
//        giftCertificateFromDb.setId(1L);
//        giftCertificateFromDb.setTags(tags);
//        assertEquals(giftCertificateFromDb.getDescription(), giftCertificateDAO.update(giftCertificateFromDb).get().getDescription());
//        giftCertificateFromDb.setId(100L);
//        assertFalse(giftCertificateDAO.update(giftCertificateFromDb).isPresent());
//    }
//
//
//    @Test
//    void delete() {
//        giftCertificateDAO.delete(5L);
//        assertFalse(giftCertificateDAO.findById(5L).isPresent());
//        assertThrows(EntityNotFoundException.class,() -> giftCertificateDAO.delete(100L));
//    }


}
