package dearest.dearestshop.service;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final MemberService memberService;

    /**
     * 배송지 추가
     */
    @Transactional
    public void addDelivery() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    }


}
