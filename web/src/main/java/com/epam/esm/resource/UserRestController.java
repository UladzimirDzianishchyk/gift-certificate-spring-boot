package com.epam.esm.resource;


import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.UserDto;
import com.epam.esm.User;
import com.epam.esm.pagination_and_sort.PaginationAndSort;
import com.epam.esm.service.UserService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *User rest controller
 *  api/v1//users
 */
@RestController
@RequestMapping(path = "/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get user by id
     *  /{id}
     * @param id int
     * @return userDto
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    /**
     *Get all users by page and sort
     * @param maxResult int
     * @param page int
     * @param sort String
     * @return PaginationAndSort<userDto>
     */
    @GetMapping
    public ResponseEntity<PaginationAndSort<UserDto>> showAll(@RequestParam int maxResult, int page, String sort) {
        PaginationAndSort<User> paginationAndSort = new PaginationAndSort<>(page, maxResult);
        paginationAndSort.setSort(sort);
        PaginationAndSort<UserDto> result = userService.find(paginationAndSort);
        return addLink(result);

    }

    /**
     *Buy certificate
     * /buy-certificate
     * @param certId Long
     * @param userId Long
     * @return PaginationAndSort<orderDto>
     */
    @GetMapping(path = "/buy-certificate")
    public ResponseEntity<OrderDTO> buyCertificate(@RequestParam Long certId, Long userId){
        return new ResponseEntity<>(userService.buyCertificate(certId,userId),HttpStatus.OK);
    }

    /**
     *Get user by part name by page and sort
     *  /get-by-name
     * @param maxResult int
     * @param page int
     * @param sort String
     * @param name String (findBy)
     * @return PaginationAndSort<userDto>
     */
    @GetMapping(path = "/get-by-name")
    public ResponseEntity<PaginationAndSort<UserDto>> findByName(@RequestParam int maxResult, int page, String name, String sort){
        PaginationAndSort<User> paginationAndSort = new PaginationAndSort<>(page, maxResult, name, sort);
        PaginationAndSort<UserDto> result = userService.findByName(paginationAndSort);
        return addLink(result);
    }


    private ResponseEntity<PaginationAndSort<UserDto>> addLink(PaginationAndSort<UserDto> result) {
        String linkToController = WebMvcLinkBuilder.linkTo(UserRestController.class) + "?maxResult=";
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

