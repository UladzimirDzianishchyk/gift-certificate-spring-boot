package com.epam.esm.resource;


import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.Order;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.service.OrderService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable long id) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PaginationAndSort<OrderDTO>> showAll(@RequestParam int maxResult, int page, String sort) {
        PaginationAndSort<Order> paginationAndSort = new PaginationAndSort<>(maxResult, page);
        paginationAndSort.setSort(sort);
        PaginationAndSort<OrderDTO> result = orderService.find(paginationAndSort);
        return addPageLink(result);
    }

    @GetMapping(path = "/get-orders-from-user")
    public ResponseEntity<PaginationAndSort<OrderDTO>> findOrderByUserId(@RequestParam int maxResult, int page, String userId, String sort){
        PaginationAndSort<Order> paginationAndSort = new PaginationAndSort<>(page, maxResult, userId, sort);
        PaginationAndSort<OrderDTO> result = orderService.findOrderByUser(paginationAndSort);
        return addPageLink(result);
    }

    private ResponseEntity<PaginationAndSort<OrderDTO>> addPageLink(PaginationAndSort<OrderDTO> result) {

        List<OrderDTO> orders = result.getResultList();
        for (OrderDTO o : orders) {
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(OrderRestController.class)
                            .findById(o.getId()))
                    .withRel("Certificate " + o.getId());
            o.add(selfLink);
        }

        String linkToController = WebMvcLinkBuilder.linkTo(OrderRestController.class) + "?maxResult=";
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
}
