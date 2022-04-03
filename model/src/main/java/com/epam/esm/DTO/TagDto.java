package com.epam.esm.DTO;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class TagDto extends RepresentationModel<TagDto> {

    private Long id;

    private String name;
}
