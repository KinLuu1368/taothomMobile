package com.lab08.main.Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Productmodel")
public class ProductModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id; // Khóa chính cho ProductModel
    
    @Column(name = "Name", nullable = false, length = 50)
    String name; // Tên của model sản phẩm (ví dụ: Pro, Max, Standard)

    @Column(name = "Image", nullable = false, columnDefinition = "nvarchar(50) default 'ModelDefault.jpg'")
    String image; // Hình ảnh đại diện cho model

    @Column(name = "Price", nullable = false)
    Double price;

    @JsonIgnore
    @OneToMany(mappedBy = "productModel")
    List<Product> products; // Quan hệ 1-N với bảng Product

    // Quan hệ với Series (nhiều sản phẩm thuộc một series)
    @ManyToOne
    @JoinColumn(name = "Seriesid")
    Series series;
}
