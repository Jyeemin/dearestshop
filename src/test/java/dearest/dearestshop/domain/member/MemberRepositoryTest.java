package dearest.dearestshop.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
        //같은 영속성 컨텍스트 안에서는 id값이 같으면 같은 엔티티로 식별 (hashcode 설정 없이도)

    /*
    @Test
    public void testMember() throws Exception{
    //given
    Member member = new Member();
    member.setUsername("memberA");

    //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(member.getId());

    //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

    }
*/

    }

