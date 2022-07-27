package org.efire.net.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(
        name = "product",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_PRODUCT_SLUG",
                columnNames = "product_slug"
        )
)
public class Product {

    @Id
    private Long id;
    private String name;
    private String description;
    @NaturalId
    @Column(name = "product_slug")
    private String productSlug;

    public Long getId() {
        return id;
    }

    public Product setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getProductSlug() {
        return productSlug;
    }

    public Product setProductSlug(String productSlug) {
        this.productSlug = productSlug;
        return this;
    }
}
