package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    // 인터페이스(MemberRepository, DiscountPolicy)와 구현체(MemoryMemberRepository, FixDiscountPolicy, RateDiscountPolicy) 모두에게 의존관계를 갖는다. -> OCP, DIP 준수 X
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // 인터페이스에만 의존하도록 설계와 코드를 변경 -> NullPointerException 발생
    // 이 문제를 해결하려면 누군가(AppConfig)가 OrderServiceImpl에 DiscountPolicy의 구현체를 대신 생성하고 주입해주어야 한다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // Dependency Injection
    // @RequiredArgsConstructor로 대체 가능
    @Autowired // 생성자가 하나뿐인 경우 생략 가능
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
