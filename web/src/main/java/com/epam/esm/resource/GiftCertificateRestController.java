package com.epam.esm.resource;

import com.epam.esm.DTO.GiftCertificateDtoWithTags;
import com.epam.esm.DTO.TagDto;
import com.epam.esm.GiftCertificate;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/gift-certificates")
public class GiftCertificateRestController {


    private final GiftCertificateService giftCertificateService;

    public GiftCertificateRestController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }


    @GetMapping(path = "{id}")
    public ResponseEntity<GiftCertificateDtoWithTags> findById(@PathVariable long id) {
        GiftCertificateDtoWithTags result = giftCertificateService.findById(id);
        result.add(WebMvcLinkBuilder.linkTo(methodOn(GiftCertificateRestController.class)
                        .deleteById(id))
                .withRel("Delete certificate " + result.getName()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<PaginationAndSort<GiftCertificateDtoWithTags>> showAll(@RequestParam int maxResult, int page, String sort) {
        PaginationAndSort<GiftCertificate> paginationAndSort = new PaginationAndSort<>(page, maxResult);
        paginationAndSort.setSort(sort);
        PaginationAndSort<GiftCertificateDtoWithTags> result = giftCertificateService.find(paginationAndSort);
        addLinksFromResource(result);
        addLinksFromPage(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "find-by-name")
    public ResponseEntity<PaginationAndSort<GiftCertificateDtoWithTags>> getCertByName(@RequestParam int maxResult, int page, String name, String sort) {
        PaginationAndSort<GiftCertificate> paginationAndSort = new PaginationAndSort<>(page, maxResult, name, sort);
        PaginationAndSort<GiftCertificateDtoWithTags> result = giftCertificateService.getCertByName(paginationAndSort);
        addLinksFromResource(result);
        addLinksFromPage(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(path = "find-by-description")
    public ResponseEntity<PaginationAndSort<GiftCertificateDtoWithTags>> getCertByDescription(@RequestParam int maxResult, int page, String name, String sort) {
        PaginationAndSort<GiftCertificate> paginationAndSort = new PaginationAndSort<>(page, maxResult, name, sort);
        PaginationAndSort<GiftCertificateDtoWithTags> result = giftCertificateService.getCertByDescription(paginationAndSort);
        addLinksFromResource(result);
        addLinksFromPage(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        return new ResponseEntity<>(giftCertificateService.deleteById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GiftCertificateDtoWithTags> save(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificateDtoWithTags result = giftCertificateService.newCertificate(giftCertificate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PatchMapping
    public ResponseEntity<GiftCertificateDtoWithTags> update(@RequestBody GiftCertificate giftCertificate) {
        return new ResponseEntity<>(giftCertificateService.update(giftCertificate), HttpStatus.OK);
    }

    @PatchMapping(path = "update-price")
    public ResponseEntity<GiftCertificateDtoWithTags> updatePrice(@RequestParam Long id, Double price){
        return new ResponseEntity<>(giftCertificateService.updatePrice(id, price),HttpStatus.OK);
    }


    @GetMapping(path = "get-cert-by-tags")
    public ResponseEntity<Set<GiftCertificateDtoWithTags>> getCertByTags(@RequestParam String ... name) {
        return new ResponseEntity<>(giftCertificateService.getCertByTags(name), HttpStatus.OK);
    }

    private void addLinksFromResource(PaginationAndSort<GiftCertificateDtoWithTags> result) {
        List<GiftCertificateDtoWithTags> certificates = result.getResultList();
        for (GiftCertificateDtoWithTags cert : certificates) {
            Set<TagDto> tags = cert.getTags();
            for (TagDto t : tags) {
                Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(TagRestController.class)
                                .findById(t.getId()))
                        .withRel("Tag " + t.getName());
                t.add(selfLink);
            }
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(GiftCertificateRestController.class)
                            .findById(cert.getId()))
                    .withRel("Certificate " + cert.getName());
            cert.add(selfLink);
        }
    }

    private void addLinksFromPage(PaginationAndSort<GiftCertificateDtoWithTags> result) {
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
                    "&page=1" +
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
    }
}
