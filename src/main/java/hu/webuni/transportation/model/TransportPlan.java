package hu.webuni.transportation.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
public class TransportPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal profit;

    @OneToMany(mappedBy = "transportPlan",cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Section> planSections;

}
