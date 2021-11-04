package com.murilonerdx.doemais.dto;

import com.murilonerdx.doemais.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUri;
    private LocalDateTime dueDate;

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();;
        imageUri = entity.getImageUri();
    }
}
