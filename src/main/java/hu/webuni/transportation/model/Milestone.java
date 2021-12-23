package hu.webuni.transportation.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder(toBuilder = true, builderMethodName = "hiddenInternalBuilder")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Enumerated(EnumType.STRING)
    private Ordinal ordinal;

    @NotNull
    private LocalDateTime plannedTime;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Address address;

    @ManyToOne
    private TransportPlan transportPlan;

    public static Milestone.MilestoneBuilder builder(Address address) {
        return hiddenInternalBuilder().address(address);
    }


}
