package org.efire.net.repository;

import org.efire.net.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductSlug(String slug);

    @Query(value =
            """
            select count(p.id) = 1
            from Product p
            where p.productSlug = :slug
            """
    )
    boolean existsBySlugWithCount(@Param("slug") String slug);

    @Query(value =
            """
            SELECT 
                CASE WHEN EXISTS(
                    SELECT 1
                    FROM product
                    where product_slug = :slug
                )
                THEN 'true'
                ELSE 'false'
                END
            """,
            nativeQuery = true
    )
    boolean existsBySlugWithCase(@Param("slug") String slug);
}
