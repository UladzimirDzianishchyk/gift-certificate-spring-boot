package com.epam.esm.serviceimpl;

import com.epam.esm.DTO.GiftCertificateDtoWithTags;
import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.EntityIsExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;

    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }


    @Transactional
    @Override
    public GiftCertificateDtoWithTags newCertificate(GiftCertificate giftCertificate) {
        if (giftCertificateDAO.findOneByName(giftCertificate.getName())
                .isPresent()) throw  new EntityIsExistException("Certificate " + giftCertificate.getName());
        GiftCertificate certificate = giftCertificateDAO.save(loadTagsToSave(giftCertificate))
                .orElseThrow(() -> new EntityNotFoundException(giftCertificate.getName()));

        return ObjectMapperUtils.map(certificate,GiftCertificateDtoWithTags.class);
    }

    @Transactional
    @Override
    public GiftCertificateDtoWithTags update(GiftCertificate giftCertificate) {
        GiftCertificate certificate = giftCertificateDAO.update(loadTagsToSave(giftCertificate))
                .orElseThrow(() -> new EntityNotFoundException("Certificate " + giftCertificate.getId()));
        return ObjectMapperUtils.map(certificate,GiftCertificateDtoWithTags.class);
    }

    @Override
    public GiftCertificateDtoWithTags updatePrice(Long id, Double price) {
        return ObjectMapperUtils.map(giftCertificateDAO.updatePrice(id, price)
                .orElseThrow(() -> new EntityNotFoundException("Certificate" + id)), GiftCertificateDtoWithTags.class);
    }


    @Override
    public GiftCertificateDtoWithTags findById(Long id) {
        GiftCertificate certificate = giftCertificateDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certificate " + id));
        return ObjectMapperUtils.map(certificate,GiftCertificateDtoWithTags.class);
    }

    @Override
    public PaginationAndSort<GiftCertificateDtoWithTags> find(PaginationAndSort<GiftCertificate> paginationAndSort) {
        PaginationAndSort<GiftCertificate> certificateFromDb = giftCertificateDAO.find(paginationAndSort);
        return mapOrEntityNotFound(paginationAndSort, certificateFromDb);
    }

    @Override
    public String deleteById(Long id) {
        return giftCertificateDAO.delete(id);
    }


    @Transactional
    @Override
    public Set<GiftCertificateDtoWithTags> getCertByTags(String... name) {
        Set<GiftCertificate> certificates = new HashSet<>();
        Set<GiftCertificateDtoWithTags> certificatesDto = new HashSet<>();
        for (String i : name) {
            Set<Tag> tags = tagDAO.findByName(i);
            for (Tag t : tags)
            certificates.addAll(t.getCertificates());
        }

        for (GiftCertificate c : certificates) {
            certificatesDto.add(ObjectMapperUtils.map(c, GiftCertificateDtoWithTags.class));
        }
        return certificatesDto;
    }

    @Override
    public PaginationAndSort<GiftCertificateDtoWithTags> getCertByName(PaginationAndSort<GiftCertificate> paginationAndSort) {
        PaginationAndSort<GiftCertificate> certificateFromDb = giftCertificateDAO.findByName(paginationAndSort);
        return mapOrEntityNotFound(paginationAndSort, certificateFromDb);
    }

    @Override
    public PaginationAndSort<GiftCertificateDtoWithTags> getCertByDescription(PaginationAndSort<GiftCertificate> paginationAndSort) {
        PaginationAndSort<GiftCertificate> certificateFromDb = giftCertificateDAO.findByDescription(paginationAndSort);
        return mapOrEntityNotFound(paginationAndSort, certificateFromDb);
    }

    private PaginationAndSort<GiftCertificateDtoWithTags> mapOrEntityNotFound(PaginationAndSort<GiftCertificate> paginationAndSort, PaginationAndSort<GiftCertificate> certificateFromDb) {
        if (certificateFromDb.getResultList().isEmpty()) throw new EntityNotFoundException(paginationAndSort.getFindBy());
        PaginationAndSort<GiftCertificateDtoWithTags> result = ObjectMapperUtils.convert(certificateFromDb);
        List<GiftCertificateDtoWithTags> resultList = ObjectMapperUtils
                .mapAll(certificateFromDb.getResultList(),GiftCertificateDtoWithTags.class);
        result.setResultList(resultList);
        return result;
    }

    GiftCertificate loadTagsToSave(GiftCertificate giftCertificate) {
        Set<Tag> tags = giftCertificate.getTags();
        Set<Tag> loadTags = new HashSet<>();

        for (Tag t : tags) {
            Optional<Tag> tag = tagDAO.findOneByName(t.getName());
            if (tag.isPresent())
                loadTags.add(tag.get());
            else
                loadTags.add(t);
        }
        giftCertificate.setTags(loadTags);
        return giftCertificate;
    }

}
