package com.excel.in.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor  // Add this annotation to generate a no-argument constructor
@Entity
@Table(name = "employee")
public class Employee {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private int uniqueDataId;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String mobileNumber;

        @Column(nullable = false)
        private String jobTitle;

        @Column(nullable = false)
        private String jobLocation;

        private String address;

        @Column(nullable = false)
        private String city;

        @Column(nullable = false)
        private String state;

        @Column(nullable = false)
        private String country;
}
