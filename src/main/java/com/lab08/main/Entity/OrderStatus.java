package com.lab08.main.Entity;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Orderstatus")
public class OrderStatus implements Serializable {
    @Id
    Integer id; // Không sử dụng tự tăng
    String status; // Trạng thái đơn hàng như 'Đang xử lý', 'Đã giao', 'Đã hủy'
}
