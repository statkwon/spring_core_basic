package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // 인터페이스와 구현체 모두에게 의존관계를 갖는다. -> OCP, DIP 준수 X
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
