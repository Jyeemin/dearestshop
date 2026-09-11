package dearest.dearestshop.service;

import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.order.Delivery;
import dearest.dearestshop.repository.AddressRepository;
import dearest.dearestshop.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final MemberService memberService;
    private final AddressRepository addressRepository;

    /**
     * 배송지 추가
     */
    @Transactional
    public Long addDelivery(Long addressId, String receiverName, String deliveryMessage) {
        Address add = addressRepository.findById(addressId).get();

        Delivery delivery = Delivery.createDelivery(
                add.getZoneCode(),
                add.getRoadAddress(),
                add.getDetailAddress(),
               receiverName,
                deliveryMessage
        );

        Delivery save = deliveryRepository.save(delivery);
        return save.getId();
    }

    @Transactional
    public Delivery findOne(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).get();
    }


}
