package com.epam.esm.dao;

import com.epam.esm.GiftCertificate;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GiftCertificateDAO extends JpaRepository<GiftCertificate, Long> {

//    Optional<GiftCertificate> save(GiftCertificate giftCertificate);
//    Optional<GiftCertificate> update(GiftCertificate giftCertificate);
//    Optional<GiftCertificate> updatePrice(Long id, Double price);
//    Optional<GiftCertificate> findById(Long id);
//    PaginationAndSort<GiftCertificate> findByName(PaginationAndSort<GiftCertificate> paginationAndSort);
//    Optional<GiftCertificate> findOneByName(String name);
//    PaginationAndSort<GiftCertificate> findByDescription(PaginationAndSort<GiftCertificate> paginationAndSort);
//    PaginationAndSort<GiftCertificate> find(PaginationAndSort<GiftCertificate> paginationAndSort);
//    String delete(Long id);

}
