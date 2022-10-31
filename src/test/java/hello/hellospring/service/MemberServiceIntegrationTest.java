package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired private MemberService service;
    @Autowired private MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        service.join(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        service.join(member2);

        // when
        List<Member> actual = service.findMembers();

        // then
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    void 회원이름_중복체크() {

        // given
        Member member1 = new Member();
        member1.setName("spring1");
        service.join(member1);

        Member member2 = new Member();
        member2.setName("spring1");
        String expected = "중복된 이름이 존재합니다.";

        // when
        IllegalStateException actual = Assertions.assertThrows(IllegalStateException.class, () -> {
            service.join(member2);
        });

        // then
        assertThat(actual.getMessage()).isEqualTo(expected);
    }
}