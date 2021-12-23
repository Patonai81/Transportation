package hu.webuni.transportation.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
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
    @ManyToOne(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Milestone fromMilestone;

    @NotNull
    @ManyToOne(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Milestone toMilestone;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TransportPlan transportPlan;


    public Section(final Long id, final Integer number, final Milestone fromMilestone, final Milestone toMilestone, final TransportPlan transportPlan) {
        this.id = id;
        this.number = number;
        fromMilestone.setOrdinal(Ordinal.FROM);
        this.fromMilestone = fromMilestone;
        toMilestone.setOrdinal(Ordinal.TO);
        this.toMilestone = toMilestone;
        this.transportPlan = transportPlan;
    }



}
