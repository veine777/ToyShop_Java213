package org.top.toyshop_java213.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "category_t")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title_t", nullable = false)
    private String title;

    @Column(name = "description_t", nullable = false)
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<ProductCategory> productCategorySet;

    public Category() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProductCategory> getProductCategorySet() {
        return productCategorySet;
    }

    public void setProductCategorySet(Set<ProductCategory> productCategorySet) {
        this.productCategorySet = productCategorySet;
    }

    @Override
    public String toString() {
        return id + " - " + title;
    }
}
