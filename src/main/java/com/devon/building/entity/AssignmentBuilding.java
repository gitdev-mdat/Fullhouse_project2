package com.devon.building.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="assignmentbuilding")
@Getter
@Setter
@NoArgsConstructor
public class AssignmentBuilding extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="buildingId",nullable=false)
    private Building building;

    @ManyToOne
    @JoinColumn(name="staffId",nullable=false)
    private User user;
}
