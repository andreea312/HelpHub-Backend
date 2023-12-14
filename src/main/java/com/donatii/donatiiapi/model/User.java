package com.donatii.donatiiapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_sequence"
    )
    private Long id;
    private String username;
    private String email;
    @Column(updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String parola;
    private String fullName;
    private GenderType gender;
    private Long points;
    private Integer nrDonations;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Donatie> donatii;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Cauza> cauze;

    @ManyToMany
    @JoinTable(
        name ="user_achievement",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns =@JoinColumn(name = "achievement_id")
    )
    private Set<Achievement> achievements;
}
