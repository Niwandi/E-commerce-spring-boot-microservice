package com.programmingTechieYt.Order_Service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

//@Entity
//@Table(name = "t_order_line_item")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class OrderLineItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    private  String skuCode;
//    private BigDecimal price;
//    private  Integer quantity;
//}


@Entity
@Table(name = "t_order_line_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String skuCode;

    private BigDecimal price;

    private Integer quantity;
}

