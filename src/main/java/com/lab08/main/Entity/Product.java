package com.lab08.main.Entity;

import java.io.Serializable;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data

@Entity
@Table(name = "Products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    
    String image;
    Double price;

    @Temporal(TemporalType.DATE)
    @Column(name = "Createdate")
    Date createDate = new Date();

    Boolean available;

    // Quan hệ với Category (nhiều sản phẩm thuộc một danh mục)
    @ManyToOne
    @JoinColumn(name = "Categoryid")
    Category category;

    // Quan hệ với bảng ProductModels
    @ManyToOne
    @JoinColumn(name = "Productmodelid")
    ProductModel productModel;

    // Quan hệ với OrderDetail (một sản phẩm có thể có nhiều chi tiết đơn hàng)
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "Productcolorsid")
    private ProductColors productColor;

    @ManyToOne
    @JoinColumn(name = "Productcapacitiesid")
    private ProductCapacities productCapacity;
}
