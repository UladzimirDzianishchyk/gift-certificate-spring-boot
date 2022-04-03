package com.epam.esm.service;


import com.epam.esm.DTO.TagDto;
import com.epam.esm.Tag;
import com.epam.esm.daoImpl.TagDAOImpl;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.serviceimpl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagServiceTest {

    TagDAOImpl tagDAOMoc = Mockito.mock(TagDAOImpl.class);
    Tag tag = new Tag();
    TagDto tagDto = new TagDto();
    PaginationAndSort<Tag> paginationAndSort = new PaginationAndSort<>();
    PaginationAndSort<TagDto> paginationAndSortDto = new PaginationAndSort<>();
    List<Tag> tags = new ArrayList<>();

    TagService tagService = new TagServiceImpl( tagDAOMoc);


    @Test
    void findById() {
        Mockito.when(tagDAOMoc.findById(1L)).thenReturn(Optional.of(tag));
        assertEquals(tagDto,tagService.findById(1L));

        assertThrows(EntityNotFoundException.class, () -> tagService.findById(100L));

    }

    @Test
    void findByName() {
        Mockito.when(tagDAOMoc.findOneByName("testTag")).thenReturn(Optional.of(tag));
        assertEquals(tag,tagService.findByName("testTag"));
        Throwable thrown = assertThrows(EntityNotFoundException.class, () -> tagService.findByName(""));
        assertNotNull(thrown.getMessage());
    }

    @Test
    void showAll() {
        tags.add(tag);
        paginationAndSort.setResultList(tags);
        Mockito.when(tagDAOMoc.find(paginationAndSort)).thenReturn(paginationAndSort);
        assertEquals(paginationAndSortDto,tagService.find(paginationAndSort));

    }
}