package com.donatii.donatiiapi.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DiscriminatorFormula;

import java.util.HashSet;
import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cauze")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cauza {
    @Id
    @SequenceGenerator(
            name = "cauza_sequence",
            sequenceName = "cauza_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cauza_sequence"
    )
    protected Long id;
    @Column(length = 1000)
    protected String descriere;
    protected String titlu;
    protected String locatie;
    protected Integer sumaMinima;
    protected Integer sumaStransa;
    protected String moneda;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "cauze_poze",
            joinColumns = @JoinColumn(name = "cauza_id"),
            inverseJoinColumns = @JoinColumn(name = "poze_id"))
    private Set<Poza> poze;
}
