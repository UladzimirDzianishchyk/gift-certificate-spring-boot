package com.epam.esm.DTO;


import com.epam.esm.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserDto extends RepresentationModel<OrderDTO> {

    private Long id;

    private String userName;

    private boolean isActive;

    private LocalDateTime registrationDate;

}
