package hu.webuni.transportation.repository.specification;

import hu.webuni.transportation.dto.AddressSearchDTO;
import hu.webuni.transportation.model.Address;
import hu.webuni.transportation.model.Address_;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class AddressSpecification implements Specification<Address> {

    AddressSearchDTO addressSearchDTO;

    @Override
    public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new LinkedList<>();


        if (StringUtils.hasText(addressSearchDTO.getCity())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Address_.city)), addressSearchDTO.getCity().toLowerCase()+"%"));
        }
        if (StringUtils.hasText(addressSearchDTO.getStreet())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Address_.street)), addressSearchDTO.getStreet().toLowerCase()+"%"));
        }
        if (StringUtils.hasText(addressSearchDTO.getCountryCode())) {
            predicates.add(criteriaBuilder.equal(root.get(Address_.street), addressSearchDTO.getCountryCode()));
        }
        if (StringUtils.hasText(addressSearchDTO.getPostalCode())) {
            predicates.add(criteriaBuilder.equal(root.get(Address_.postalCode), addressSearchDTO.getPostalCode()));
        }

        return criteriaQuery
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .distinct(true)
                .getRestriction();
    }
}
