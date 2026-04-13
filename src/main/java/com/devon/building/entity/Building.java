package com.devon.building.entity;

import com.devon.building.enums.Type;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "building")
public class Building implements Serializable {

    @Serial
    private static final long serialVersionUID = -1000119078147252957L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "street", length = 255, nullable = false)
    private String street;

    @Column(name = "ward", length = 255, nullable = false)
    private String ward;

    @Column(name = "district", length = 255, nullable = false)
    private String district;

    @Column(name = "rentprice", nullable = false)
    private double price;

    @Column(name="structure")
    private String structure;

    @Column (name="numberofbasement")
    private Integer numberOfBasement;

    @Column (name="floorarea")
    private Integer floorArea;

    @Column(name="direction")
    private String direction;

    @Column(name="level")
    private String level;

    @Column(name="rentpricedescription")
    String rentPriceDescription;

    @Column(name="servicefee")
    String serviceFee;

    @Column(name="carfee")
    String carFee;

    @Column(name="motofee")
    String motoFee;

    @Column(name="overtimefee")
    String overTimeFee;

    @Column(name="waterfee")
    String waterFee;

    @Column(name="electricityfee")
    String electricityFee;

    @Column(name="deposit")
    String deposit;

    @Column(name="payment")
    String payment;

    @Column(name="renttime")
    String rentTime;

    @Column(name="decorationtime")
    String decorationTime;

    @Column(name="brokeragefee")
    BigDecimal brokerageFee;

    @Column(name="note")
    String note;

    @Column(name="linkofbuilding")
    String linkOfBuilding;

    @Column
    String map;

    @Column(name="managername")
    String managerName;

    @Column(name="managerphone")
    String managerPhone;
    
    @Lob
    @Column(name = "image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createddate", nullable = false)
    private Date createDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "assignmentbuilding",
            joinColumns = @JoinColumn(name = "buildingId"),
            inverseJoinColumns = @JoinColumn(name = "staffId")
    )
    private Set<User> staffs;

    @OneToMany(mappedBy="building")
    @JsonManagedReference
    private List<RentArea> rentAreas;

    @Column(name = "type", nullable = false)
    private String type;
}
