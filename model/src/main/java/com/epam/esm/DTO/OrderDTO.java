package com.epam.esm.DTO;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
public class OrderDTO extends RepresentationModel<OrderDTO> {

    private Long id;

    @JsonIgnoreProperties("tags")
    private GiftCertificateDtoWithTags certificate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.ms")
    private LocalDateTime orderDate;

    private Double price;

    private Status status;
}
