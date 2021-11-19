package com.murilonerdx.doemais.entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.IndexColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("deprecation")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_BUSINESS")
public class Business implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String website;
    @NotEmpty
    private String address;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Userman user;

    private double tribute;
    private double points;


    @JsonIgnore
    @OneToMany(orphanRemoval = true, mappedBy ="business", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @IndexColumn(name = "INDEX_COLUMN")
    private List<Product> products = new LinkedList<>();

}
