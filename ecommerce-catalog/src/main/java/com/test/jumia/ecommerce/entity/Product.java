package com.test.jumia.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="Product", uniqueConstraints = { @UniqueConstraint(columnNames = { "sku", "country" }) })
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @CsvBindByName
    String sku;
    @CsvBindByName
    String name;
    @CsvBindByName
    String country;
    @CsvBindByName(column = "stock_change")
    @JsonProperty("stock_change")
    @Column(name = "stock_change")
    String stockChange;
}
