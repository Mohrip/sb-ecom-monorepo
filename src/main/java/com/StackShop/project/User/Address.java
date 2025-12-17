package com.StackShop.project.User;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;


    @NotBlank
    @Size(min = 5, message = "Street must be at least 5 characters long")
    private String street;

    @NotBlank
    @Size(min = 5, message = "Building name must be at least 5 character long")
    private String buildingName;

    @NotBlank
    @Size(min = 5, message = "city name must be at least 5 character long")
    private String city;

    @NotBlank
    @Size(min = 2, message = "state name must be at least 2 character long")
    private String state;

    @NotBlank
    @Size(min = 6, message = "zipCode name must be at least 6 character long")
    private String zipCode;

    @NotBlank
    @Size(min = 2, message = "country name must be at least 2 character long")
    private String country;



    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String street, String buildingName, String city, String state, String zipCode, String country) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }
}
