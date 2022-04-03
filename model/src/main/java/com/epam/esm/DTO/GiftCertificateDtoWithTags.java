package com.epam.esm.DTO;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class GiftCertificateDtoWithTags extends RepresentationModel<GiftCertificateDtoWithTags> {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Long duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    private Set<TagDto> tags;

}
