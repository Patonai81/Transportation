package hu.webuni.transportation.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder(toBuilder = true, builderMethodName = "hiddenInternalBuilder")
@NoArgsConstructor
@Getter

@Entity
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotNull
    private LocalDateTime plannedTime;

    @NotNull
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Address address;

    public static Milestone.MilestoneBuilder builder(Address address) {
        return hiddenInternalBuilder().address(address);
    }


}
