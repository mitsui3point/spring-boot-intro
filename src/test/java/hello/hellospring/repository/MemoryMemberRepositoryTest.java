package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {
    private MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach
    void tearDown() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Member expected = new Member();
        expected.setName("spring");

        //when
        Member actual = memberRepository.save(expected);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findById() {
        //given
        Member member = new Member();
        member.setName("spring");
        Member expected = memberRepository.save(member);

        //when
        Member actual = memberRepository.findById(expected.getId()).get();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByName() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring2");
        Member expected = memberRepository.save(member2);

        //when
        Member actual = memberRepository.findByName("spring2").get();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberRepository.save(member2);

        List<Member> expected = Arrays.asList(new Member[]{member1, member2});

        //when
        List<Member> actual = memberRepository.findAll();

        //then
        assertThat(actual).isEqualTo(expected);
    }
}