package org.amitabh.mycontactapp2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name = "phoneNumber")
    private String phone;
}
