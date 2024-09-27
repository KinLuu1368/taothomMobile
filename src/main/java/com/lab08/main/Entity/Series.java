package com.lab08.main.Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data

@Entity
@Table(name = "Series")
public class Series implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;  // Khóa chính cho Series
    
    @Column(name = "Name")
    String name; // Tên series (ví dụ: iPhone 12, Samsung Galaxy S21)

    @JsonIgnore
    @OneToMany(mappedBy = "series")
    List<ProductModel> productModel; // Quan hệ 1-N với sản phẩm (một series có nhiều mẫu sản phẩm)
}
