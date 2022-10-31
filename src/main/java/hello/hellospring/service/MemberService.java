package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public void join(Member member) {
        duplicateValidName(member);
        memberRepository.save(member);
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
        return memberRepository.findAll();
    }
}
