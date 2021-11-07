package com.murilonerdx.doemais.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.murilonerdx.doemais.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TB_PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imageUri;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @NotNull(message="Digite uma data de vencimento")
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
