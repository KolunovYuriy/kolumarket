package ru.kolumarket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kolumarket.domain.Product;
import ru.kolumarket.dto.ProductDTO;
import ru.kolumarket.repository.ProductRepository;
import ru.kolumarket.repository.SortDirection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<ProductDTO> getById(Long id) {
        return productRepository.findById(id).map(ProductDTO::new);
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

    public  Page<ProductDTO> getAll(Integer leftCost, Integer rightCost, Integer page, Integer size, String[] sort){
        Page<ProductDTO> result = null;

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

    public ProductDTO updateProduct(Product product){
        return new ProductDTO(productRepository.save(product));
    }

    public Page<ProductDTO> getProductsLikeTitle(String like, Integer page, Integer size, String[] sort) {
        return productRepository.getProductsLikeTitle(like,getPageableFromRequest(page,size,sort)).map(ProductDTO::new);
//        return productRepository.getProductsByTitleIsLike(like);
    }
}
