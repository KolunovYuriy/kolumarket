package ru.kolumarket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kolumarket.domain.Category;
import ru.kolumarket.domain.Product;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private int price;
    private String category;

    public ProductDTO(Product p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.price = p.getPrice();
        this.category = p.getCategory().getTitle();
    }

}
