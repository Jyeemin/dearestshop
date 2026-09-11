package dearest.dearestshop.repository;

import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    boolean existsByMember(Member member);

    Optional<Address> findByMemberAndIsDefaultTrue(Member member);

    List<Address> findByMember(Member member);

    List<Address> findByMemberOrderByAddressIdDesc(Member member);
}
