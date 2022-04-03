package com.epam.esm.service;


import com.epam.esm.DTO.GiftCertificateDtoWithTags;
import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.serviceimpl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GiftCertificateServiceTest {

//    GiftCertificateDAO giftCertificateDAOMoc = Mockito.mock(GiftCertificateDAO.class);
//    TagDAO tagDAOMoc = Mockito.mock(TagDAO.class);
//    GiftCertificate giftCertificate = new GiftCertificate("cert", "desc", 1.2, 5L);
//    GiftCertificateDtoWithTags giftCertificateDtoWithTags = new GiftCertificateDtoWithTags();
//    PaginationAndSort<GiftCertificate> paginationAndSort = new PaginationAndSort<>();
//    PaginationAndSort<GiftCertificateDtoWithTags> paginationAndSortDto = new PaginationAndSort<>();
//    Tag tag = new Tag("tag");
//    List<GiftCertificate> giftCertificates = new LinkedList<>();
//    List<GiftCertificateDtoWithTags> giftCertificatesDto = new LinkedList<>();
//    Set<Tag> tags = new HashSet<>();
//
//    GiftCertificateService giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDAOMoc, tagDAOMoc);
//
//
//    @BeforeEach
//    void beforeClass() {
//        tag.setId(1L);
//        tags.add(tag);
//        giftCertificate.setId(1L);
//        giftCertificate.setTags(tags);
//        giftCertificates.add(giftCertificate);
//        giftCertificateDtoWithTags.setId(1L);
//        paginationAndSort.setResultList(giftCertificates);
//        paginationAndSortDto.setResultList(giftCertificatesDto);
//        Mockito.when(tagDAOMoc.findOneByName(tag.getName())).thenReturn(Optional.of(tag));
//        Mockito.when(tagDAOMoc.findById(tag.getId())).thenReturn(Optional.of(tag));
//        Mockito.when(giftCertificateDAOMoc.findById(1L)).thenReturn(Optional.of(giftCertificate));
//
//    }
//
//
//    @Test
//    void addGiftCertificate() {
//        Mockito.when(giftCertificateDAOMoc.save(giftCertificate)).thenReturn(Optional.of(giftCertificate));
//        assertEquals(giftCertificateDtoWithTags.getId(), giftCertificateService.newCertificate(giftCertificate).getId());
//    }
//
//    @Test
//    void update() {
//        Mockito.when(giftCertificateDAOMoc.update(giftCertificate)).thenReturn(Optional.of(giftCertificate));
//        assertEquals(giftCertificateDtoWithTags.getId(), giftCertificateService.update(giftCertificate).getId());
//        Mockito.when(giftCertificateDAOMoc.update(giftCertificate)).thenThrow(EntityNotFoundException.class);
//        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.update(giftCertificate));
//    }
//
//    @Test
//    void findById() {
//        assertEquals(giftCertificate.getId(), giftCertificateService.findById(1L).getId());
//        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.findById(5L));
//    }
//
//    @Test
//    void find() {
//        Mockito.when(giftCertificateDAOMoc.find(paginationAndSort)).thenReturn(paginationAndSort);
//        assertEquals(paginationAndSortDto, giftCertificateService.find(paginationAndSort));
//        Mockito.when(giftCertificateDAOMoc.find(paginationAndSort)).thenThrow(EntityNotFoundException.class);
//        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.find(paginationAndSort));
//    }
//
//    @Test
//    void getCertName() {
//        Mockito.when(giftCertificateDAOMoc.findByName(paginationAndSort)).thenReturn(paginationAndSort);
//        assertEquals(paginationAndSortDto, giftCertificateService.getCertByName(paginationAndSort));
//        Mockito.when(giftCertificateDAOMoc.findByName(paginationAndSort)).thenThrow(EntityNotFoundException.class);
//        assertThrows(EntityNotFoundException.class,() -> giftCertificateService.getCertByName(paginationAndSort));
//    }
//
//    @Test
//    void getCertByDescription() {
//        Mockito.when(giftCertificateDAOMoc.findByDescription(paginationAndSort)).thenReturn(paginationAndSort);
//        assertEquals(paginationAndSortDto, giftCertificateService.getCertByDescription(paginationAndSort));
//        Mockito.when(giftCertificateDAOMoc.findByDescription(paginationAndSort)).thenThrow(EntityNotFoundException.class);
//        assertThrows(EntityNotFoundException.class,() -> giftCertificateService.getCertByDescription(paginationAndSort));
//    }

}