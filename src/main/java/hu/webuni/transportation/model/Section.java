package hu.webuni.transportation.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@NoArgsConstructor
@Getter

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotNull
    private Integer number;

    @NotNull
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Milestone fromMilestone;

    @NotNull
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Milestone toMilestone;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TransportPlan transportPlan;

}
