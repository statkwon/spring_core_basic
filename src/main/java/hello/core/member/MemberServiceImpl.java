package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // 인터페이스(MemberRepository)와 구현체(MemoryMemberRepository) 모두에게 의존관계를 갖는다. -> OCP, DIP 준수 X
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // Dependency Injection
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
