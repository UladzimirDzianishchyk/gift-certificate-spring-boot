package com.epam.esm.service;

import com.epam.esm.DTO.GiftCertificateDtoWithTags;
import com.epam.esm.GiftCertificate;
import com.epam.esm.pagination_and_sort.PaginationAndSort;

import java.util.Set;

public interface GiftCertificateService {
    GiftCertificateDtoWithTags newCertificate(GiftCertificate giftCertificate);

    GiftCertificateDtoWithTags update(GiftCertificate giftCertificate);

    GiftCertificateDtoWithTags updatePrice(Long id, Double Price);

    GiftCertificateDtoWithTags findById(Long id);

    PaginationAndSort<GiftCertificateDtoWithTags> find(PaginationAndSort<GiftCertificate> paginationAndSort);

    String deleteById(Long id);

    Set<GiftCertificateDtoWithTags> getCertByTags(String ... name);

    PaginationAndSort<GiftCertificateDtoWithTags> getCertByName(PaginationAndSort<GiftCertificate> paginationAndSort);

    PaginationAndSort<GiftCertificateDtoWithTags> getCertByDescription(PaginationAndSort<GiftCertificate> paginationAndSort);

}
