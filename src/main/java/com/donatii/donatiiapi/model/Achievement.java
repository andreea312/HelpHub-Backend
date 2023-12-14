package com.donatii.donatiiapi.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="achievement")
public class Achievement {
    @Id
    @SequenceGenerator(
            name = "achievement_sequence",
            sequenceName = "achievement_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "achievement_sequence"
    )
    private Long id;
    private String url;
    private Long points_required;
    private Integer nr_donations_required;

}
