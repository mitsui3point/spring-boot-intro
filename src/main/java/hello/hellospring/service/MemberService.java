package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        long start = System.currentTimeMillis();
        try {
            duplicateValidName(member);
            memberRepository.save(member);
            return member.getId();
        } finally {
            long end = System.currentTimeMillis();
            long timeMs = end - start;
            System.out.println("join " + timeMs + "ms");
        }
    }

    /**
     * 회원이름 중복체크
     */
    private void duplicateValidName(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(store -> {
            throw new IllegalStateException("중복된 이름이 존재합니다.");
        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();
        } finally {
            long end = System.currentTimeMillis();
            long timeMs = end - start;
            System.out.println("findMembers " + timeMs + "ms");
        }
    }
}
