package org.top.toyshop_java213.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "product_t")
public class Product {

    //id товара
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //наименование товара
    @Column(name = "title_f", nullable = false)
    private String title;

    //описание товара
    @Column(name = "description_f", nullable = false)
    private String description;

    //цена товара
    @Column(name = "price_f", nullable = false)
    private Double price;

    // наличие товара
    @Column(name = "is_available_f", nullable = false)
    private Boolean available;

    // строка хранит байты изображения
    @Lob
    @Column(name = "preview_image_f", columnDefinition = "MEDIUMBLOB")
    private String previewImageData;

    //связи
    @OneToMany(mappedBy = "product")
    private Set<ProductCategory> productCategorySet;

    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> orderProductSet;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviewSet;

    //конструктор
    public Product() {
        id = 0;
        title = "";
        description = "";
        price = 0.0;
        available = false;
        reviewSet = null;
    }

    //getters & setters
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<ProductCategory> getProductCategorySet() {
        return productCategorySet;
    }

    public void setProductCategorySet(Set<ProductCategory> productCategorySet) {
        this.productCategorySet = productCategorySet;
    }

    public String getPreviewImageData() {
        return previewImageData;
    }

    public void setPreviewImageData(String previewImageData) {
        this.previewImageData = previewImageData;
    }

    public Set<OrderProduct> getOrderProductSet() {
        return orderProductSet;
    }

    public void setOrderProductSet(Set<OrderProduct> orderProductSet) {
        this.orderProductSet = orderProductSet;
    }

    public Set<Review> getReviewSet() {
        return reviewSet;
    }

    public void setReviewSet(Set<Review> reviewSet) {
        this.reviewSet = reviewSet;
    }

    // переопределение метода Java toString()
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isAvailable=" + available +
                '}';
    }
}
