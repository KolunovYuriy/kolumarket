package ru.kolumarket.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kolumarket.marketservice.domain.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    List<Product> getProductsByPriceIsBetween(Integer left, Integer right);
    Page<Product> getProductsByPriceIsBetween(Integer left, Integer right, Pageable pageable);
    List<Product> getProductsByPriceIsAfter(Integer left);
    Page<Product> getProductsByPriceIsAfter(Integer left, Pageable pageable);
    List<Product> getProductsByPriceIsBefore(Integer right);
    Page<Product> getProductsByPriceIsBefore(Integer right, Pageable pageable);

    @Query("select p from Product p where lower(p.title) like %:like%")
    List<Product> getProductsLikeTitle(String like);

    @Query("select p from Product p where lower(p.title) like %:like%")
    Page<Product> getProductsLikeTitle(String like, Pageable pageable);

//    List<Product> getProductsByTitleIsLike(String like);
}
