package edu.miu.minimarket.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity

@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String role;
}
