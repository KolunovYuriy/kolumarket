package ru.kolumarket.product.dto;

public class ProductDtoBuilder {
    private final ProductDTO productDTO;

    public ProductDtoBuilder() {
        this.productDTO = new ProductDTO();
    }

    public ProductDtoBuilder id(Long productId) {
        productDTO.setId(productId);
        return this;
    }

    public ProductDtoBuilder title(String title) {
        productDTO.setTitle(title);
        return this;
    }

    public ProductDtoBuilder price(int price) {
        productDTO.setPrice(price);
        return this;
    }

    public ProductDtoBuilder amount(int price) {
        productDTO.setProductCount(price);
        return this;
    }

    public ProductDtoBuilder category(String category) {
        productDTO.setCategory(category);
        return this;
    }

    public ProductDTO build() {
        return this.productDTO;
    }

}
