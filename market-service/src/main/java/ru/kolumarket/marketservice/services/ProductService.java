package ru.kolumarket.marketservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.kolumarket.core.dto.ProductDtoCore;
import ru.kolumarket.marketservice.domain.Category;
import ru.kolumarket.marketservice.domain.Product;
import ru.kolumarket.marketservice.dto.ProductDTO;
import ru.kolumarket.core.exeptions.ResourceNotFoundException;
import ru.kolumarket.marketservice.repository.CategoryRepository;
import ru.kolumarket.marketservice.repository.ProductRepository;
import ru.kolumarket.marketservice.repository.SortDirection;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    //@Value("${kolumarket.jwt.redis_cash_expiration}")
    @Value("1")
    private String redisCashExpiration;

    public Optional<ProductDTO> getById(Long id) {
        if (redisTemplate.hasKey("getProductById " + id)) {
            return Optional.of((ProductDTO)redisTemplate.opsForValue().get("getProductById " + id));
        }
        else {
            Optional<ProductDTO> productDTO = productRepository.findById(id).map(ProductDTO::new);
            redisTemplate.opsForValue().setIfAbsent("getProductById " + id, productDTO.get(), Duration.ofHours(Long.parseLong(redisCashExpiration)));
            return productDTO;
        }
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Pageable getPageableFromRequest(Integer page, Integer size, String[] sort) {
        //for paging
        Map<String, SortDirection> sortMap = new HashMap<>();
        Arrays.stream(sort).forEach(s -> {
            String[] sortCommand = s.split(",");
            sortMap.put(sortCommand[0],sortCommand[1].toUpperCase().equals(SortDirection.ASC.toString().toUpperCase())?SortDirection.ASC:SortDirection.DESC);
        });

        String ASCSortParams = sortMap.keySet().stream().filter(key -> sortMap.get(key)==SortDirection.ASC).collect(Collectors.joining(","));
        String DESCSortParams = sortMap.keySet().stream().filter(key -> sortMap.get(key)==SortDirection.DESC).collect(Collectors.joining(","));
        //String[] DESCSortParams = sortMap.keySet().stream().filter(key -> sortMap.get(key)==SortDirection.DESC).collect(Collectors.toList()).toArray(); // TODO: если кастовать в String[], то возникает ошибка
        Pageable pageable = null;
        if ( ASCSortParams.equals("") && !DESCSortParams.equals("") ) {
            pageable = PageRequest.of(page, size, Sort.by(ASCSortParams.split(",")).ascending().and(Sort.by(DESCSortParams.split(",")).descending()));
        }
        else {
            pageable = PageRequest.of(page, size);
        }
        return pageable;
    }

    public  Page<ProductDtoCore> getAll(Integer leftCost, Integer rightCost, Integer page, Integer size, String[] sort){
        Page<ProductDtoCore> result = null;

        Pageable pageable = getPageableFromRequest(page,size,sort);

        if (leftCost != null) {
            if (rightCost != null) {
                result = productRepository.getProductsByPriceIsBetween(leftCost,rightCost,pageable).map(ProductDTO::new);
            }
            else {
                result = productRepository.getProductsByPriceIsAfter(leftCost,pageable).map(ProductDTO::new);
            }
        }
        else if (rightCost != null) {
            result = productRepository.getProductsByPriceIsBefore(rightCost,pageable).map(ProductDTO::new);
        }
        else {


            result = productRepository.findAll(pageable).map(ProductDTO::new);
        }
        return result;
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public ProductDTO addProduct(Product product){
        return new ProductDTO(productRepository.save(product));
    }

    public ProductDTO addProduct(ProductDtoCore productDTO){
        return new ProductDTO(productRepository.save(createProductFromProductDTO(productDTO)));
    }

    public ProductDTO updateProduct(ProductDtoCore productDTO){

        Product product = getProductById(productDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Product with id: " + productDTO.getId() + " doesn't exist"));

        if (!productDTO.getTitle().equals("")) {
            product.setTitle(productDTO.getTitle());
        }
        if (productDTO.getPrice()!=0) {
            product.setPrice(productDTO.getPrice());
        }
        if (!productDTO.getCategory().equals("")) {
            product.setCategory(categoryRepository.findByTitle(productDTO.getCategory()).orElseGet(() -> categoryRepository.save(new Category(productDTO.getCategory()))));
        }
        return new ProductDTO(productRepository.save(product));
    }

    private Product createProductFromProductDTO(ProductDtoCore productDTO) {
        return new Product(productDTO.getTitle(), productDTO.getPrice(), categoryRepository.findByTitle(productDTO.getCategory()).orElseGet(() -> categoryRepository.save(new Category(productDTO.getCategory()))));
    }

    public Page<ProductDTO> getProductsLikeTitle(String like, Integer page, Integer size, String[] sort) {
        return productRepository.getProductsLikeTitle(like,getPageableFromRequest(page,size,sort)).map(ProductDTO::new);
//        return productRepository.getProductsByTitleIsLike(like);
    }
}
