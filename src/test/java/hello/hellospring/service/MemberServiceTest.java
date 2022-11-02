package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceTest {

    private MemberService service;
    private MemoryMemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();
        service = new MemberService(memberRepository);
    }

    @AfterEach
    void tearDown() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");

        // when
        Long saveId = service.join(member1);

        // then
        Optional<Member> findMember = memberRepository.findById(saveId);
        String actual = findMember.get().getName();
        String expected = member1.getName();
        assertThat(actual).isEqualTo(expected);
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