package com.devon.building.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="assignmentcustomer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentCustomer extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="staffId",nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="customerId",nullable=false)
    private Customer customer;
}
