package com.epam.esm.resource;

import com.epam.esm.DTO.TagDto;
import com.epam.esm.Tag;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.service.TagService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 *Tag rest controller
 *   api/v1//tags
 */

@RestController
@RequestMapping(path = "/tags")
public class TagRestController {

    private final TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }


    /**
     * Get tag by id
     *  /{id}
     * @param id int
     * @return tagDto
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<TagDto> findById(@PathVariable long id) {
        TagDto result = tagService.findById(id);
        result.add(WebMvcLinkBuilder.linkTo(methodOn(TagRestController.class)
                        .deleteById(id))
                .withRel("Delete tag " + result.getName()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     *Get all tags by page and sort
     * @param maxResult int
     * @param page int
     * @param sort String
     * @return PaginationAndSort<TagDto>
     */
    @GetMapping
    public ResponseEntity<PaginationAndSort<TagDto>> showAll(@RequestParam int maxResult, int page, String name, String sort) {
        PaginationAndSort<Tag> paginationAndSort = new PaginationAndSort<>(page, maxResult, name, sort);
        PaginationAndSort<TagDto> result = tagService.find(paginationAndSort);
        List<TagDto> tags = result.getResultList();
        for (TagDto t : tags) {
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(TagRestController.class)
                            .deleteById(t.getId()))
                    .withRel("Delete tag " + t.getName());
            t.add(selfLink);
        }
        String linkToController = WebMvcLinkBuilder.linkTo(GiftCertificateRestController.class) + "?maxResult=";
        Link nextPage = Link.of(linkToController +
                        result.getMaxResult() +
                        "&page=" +
                        (result.getCurrentPage() + 1) +
                        "&sort=" +
                        result.getSort())
                .withRel("Next page");
        if (result.getCurrentPage() > 1) {
            Link previousPage = Link.of(linkToController +
                    result.getMaxResult() +
                    "&page=" +
                    (result.getCurrentPage() - 1) +
                    "&sort=" +
                    result.getSort()).withRel("Previous page");

            Link firstPage = Link.of(linkToController +
                    result.getMaxResult() +
                    "&page=" +
                    1 +
                    "&sort=" +
                    result.getSort()).withRel("First page");
            result.add(previousPage, firstPage);
        }

        Link lastPage = Link.of(linkToController +
                        result.getMaxResult() +
                        "&page=" +
                        result.getTotalPage() +
                        "&sort=" +
                        result.getSort())
                .withRel("Last page");

        result.add(nextPage, lastPage);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Delete tag by id
     *  /{id}
     * @param id int
     * @return String
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(tagService.deleteById(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<TagDto> save(@RequestBody Tag tag) {
        TagDto result = tagService.newTag(tag);
        result.add(WebMvcLinkBuilder.linkTo(methodOn(TagRestController.class)
                        .deleteById(result.getId()))
                .withRel("Delete tag " + result.getId()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(path = "find-most-popular-end-expensive-tag-by-user-id")
    public TagDto findMostPopularEndExpensiveTagByUserId(@RequestParam Long userId){
        return tagService.findMostPopularEndExpensiveTagByUserId(userId);
    }
}
