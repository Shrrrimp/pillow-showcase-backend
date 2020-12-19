package com.pillows.springbootpillowsapi.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="pillowTable")
public class Pillow {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "pillowName", nullable = false)
    private String pillowName;

    @Column(name = "shortDscrpt")
    private String shortDscrpt;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cloth")
    private String cloth;

    @Column(name = "fabricStructure")
    private String fabricStructure;

    @Column(name = "filler", length = 200)
    private String filler;

    @Column(name = "size")
    private String size;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "image")
    private String fileName;

    public Pillow(Integer id,
                  String pillowName,
                  String shortDscrpt,
                  String description,
                  String cloth,
                  String fabricStructure,
                  String filler,
                  String size,
                  Double price,
                  String fileName) {
        this.id = id;
        this.pillowName = pillowName;
        this.shortDscrpt = shortDscrpt;
        this.description = description;
        this.cloth = cloth;
        this.fabricStructure = fabricStructure;
        this.filler = filler;
        this.size = size;
        this.price = price;
        this.fileName = fileName;
    }

    protected Pillow() {
    }

}
