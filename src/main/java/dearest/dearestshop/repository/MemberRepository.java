package dearest.dearestshop.repository;

import dearest.dearestshop.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    //이메일로 찾기
    Optional<Member> findByEmail(String email);

    //이메일 존재하는지 확인
    boolean existsByEmail(String email);

    //이름을 포함하고있는지 확인
    List<Member> findByNameContaining(String name);

}

