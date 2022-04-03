package com.epam.esm.serviceimpl;

import com.epam.esm.DTO.TagDto;
import com.epam.esm.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.EntityIsExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Transactional
    @Override
    public TagDto newTag(Tag tag) {
        if (tagDAO.findOneByName(tag.getName()).isPresent()) throw  new EntityIsExistException("Tag " + tag.getName());
        return ObjectMapperUtils.map(tagDAO.save(tag)
                .orElseThrow(() -> new EntityNotFoundException("Tag " + tag.getName())),TagDto.class);
    }

    @Override
    public TagDto findById(Long id) {
        return ObjectMapperUtils.map(tagDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag " + id)),TagDto.class);
    }

    @Override
    public Tag findByName(String name) {

        return tagDAO.findOneByName(name).orElseThrow(() -> new EntityNotFoundException("Tag " + name));
    }

    @Override
    public PaginationAndSort<TagDto> find(PaginationAndSort<Tag> paginationAndSort) {
        PaginationAndSort<Tag> paginationAndSortFromDb = tagDAO.find(paginationAndSort);
        if (paginationAndSortFromDb.getResultList().isEmpty()) throw new EntityNotFoundException(paginationAndSort.getFindBy());
        PaginationAndSort<TagDto> result = ObjectMapperUtils.convert(paginationAndSortFromDb);
        List<TagDto> resultList = ObjectMapperUtils.mapAll(paginationAndSort.getResultList(),TagDto.class);

        result.setResultList(resultList);
        return result;
    }

    @Override
    public String deleteById(Long id) {
        return tagDAO.delete(id);
    }


    @Override
    public TagDto findMostPopularEndExpensiveTagByUserId(Long userId) {
        return ObjectMapperUtils.map(tagDAO
                .findMostPopularEndExpensiveTagByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId)),TagDto.class);
    }
}
