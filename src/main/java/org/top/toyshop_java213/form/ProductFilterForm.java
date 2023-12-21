package org.top.toyshop_java213.form;

// ProductFilterForm - форма для фильтрации товаров
public class ProductFilterForm {
    private String product; // поле для поиска по паттерну для title/description
    private Integer minPrice;
    private Integer maxPrice;

    public ProductFilterForm() {
        product = "";
        minPrice = 0;
        maxPrice = 0;
    }

    public boolean isFormEmpty() {
        return product.equals("") && minPrice == 0 && maxPrice == 0;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }
}
