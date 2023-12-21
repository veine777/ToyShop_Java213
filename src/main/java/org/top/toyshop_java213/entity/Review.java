package org.top.toyshop_java213.entity;

import jakarta.persistence.*;

import java.util.Date;

// Review описывает сущность "Отзыв о товаре" - запись в таблице отзывов
@Entity
@Table(name = "review_t")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="review_rate_f", nullable = false)
    private Double reviewRate;      // оценка отеля, поставленная автором пользователя

    @Column(name="comment_f")
    private String comment;         // комментарий отзыва

    @Column(name="written_in")
    private Date writtenIn;

    // Связь: отзыв ссылается на товар, много отзывов к одному товару
    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    //конструктор

    public Review(){
        id = 0;
        reviewRate = 0.0;
        comment = null;
        writtenIn = null;
    }
    //геттеры и сеттеры

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getReviewRate() {
        return reviewRate;
    }

    public void setReviewRate(Double reviewRate) {
        this.reviewRate = reviewRate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getWrittenIn() {
        return writtenIn;
    }

    public void setWrittenIn(Date writtenIn) {
        this.writtenIn = writtenIn;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    @Override
    public String toString() {
        return "Отзыв{" +
                "id=" + id +
                ", оценка=" + reviewRate +
                ", комментарий='" + comment + '\'' +
                '}';
    }
}
