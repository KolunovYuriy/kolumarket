package ru.kolumarket.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDtoCore {
    private Long id;
    private String title;
    private int price;
    private String category;
}
