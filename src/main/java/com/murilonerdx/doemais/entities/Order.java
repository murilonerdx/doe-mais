package com.murilonerdx.doemais.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TB_ORDER_ITEM")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @JsonFormat(pattern="dd/MM/yyyy")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private LocalDateTime moment;


    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.DETACH)
    private Product product;

    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    private Ong ong;

    public Order(Long id, LocalDateTime moment) {
        this.id = id;
        this.moment = moment;
    }
}
