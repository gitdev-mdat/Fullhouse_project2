package com.devon.building.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @Column
    private String email;

    @Column
    private String companyName;

    @Column
    private String demand;

    @Column
    private String status;

    @Column(name="is_active")
    private Boolean isActive;

}
