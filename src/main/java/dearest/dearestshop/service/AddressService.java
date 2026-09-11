package dearest.dearestshop.service;

import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.dto.addressdto.AddressCreateDto;
import dearest.dearestshop.dto.addressdto.AddressResponseDto;
import dearest.dearestshop.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {
    private final AddressRepository addressRepository;
    private final MemberService memberService;


    /**
     * 주소추가
     * @param addressCreateDto
     * @return
     */
    @Transactional
    public Long createAddress(AddressCreateDto addressCreateDto) {
        Member member = memberService.getLoginMember();
        boolean isDefault = addressCreateDto.isDefault();
        boolean isExistAddress = addressRepository.existsByMember(member);

        if (!isExistAddress) {
            isDefault = true;
        }

        if (isExistAddress && isDefault) {
            Address defaultAddress = addressRepository.findByMemberAndIsDefaultTrue(member)
                    .orElse(null);

            if (defaultAddress != null) {
                defaultAddress.changeDefault(false);
            }
        }

        Address address = Address.createAddress(addressCreateDto.getZoneCode(),
                addressCreateDto.getRoadAddress(),
                addressCreateDto.getDetailAddress(),
                isDefault,
                member);
        Address saveAddress = addressRepository.save(address);

        return saveAddress.getAddressId();
    }

    /**
     * 주소수정
     *
     * @param addressResponseDto
     * @return
     */
    @Transactional
    public Long updateAddress(AddressResponseDto addressResponseDto) {
        //기본배송지로변경
        //
        Member member = memberService.getLoginMember();

        Address findAddress = addressRepository.findById(addressResponseDto.getAddressId()).orElseThrow(
                () -> new RuntimeException("주소가 존재하지 않습니다.")
        );

        if (!findAddress.getMember().getId().equals(member.getId())) {
            throw new RuntimeException("본인의 주소만 수정할 수 있습니다.");
        }

        // 기본 배송지를 해제하려는 경우
        if (findAddress.isDefault() && !addressResponseDto.isDefault()) {

            throw new RuntimeException("기본 배송지는 해제할 수 없습니다. 다른 주소를 기본 배송지로 설정해주세요.");
        }

        // 기본 배송지로 변경하는 경우
        if (addressResponseDto.isDefault()
                && !findAddress.isDefault()) {

            Address defaultAddress =
                    addressRepository.findByMemberAndIsDefaultTrue(member)
                            .orElse(null);

            if (defaultAddress != null) {
                defaultAddress.changeDefault(false);
            }

        }

        findAddress.changeZoneCode(addressResponseDto.getZoneCode());
        findAddress.changeRoadAddress(addressResponseDto.getRoadAddress());
        findAddress.changeDetailAddress(addressResponseDto.getDetailAddress());
        findAddress.changeDefault(addressResponseDto.isDefault());
        return findAddress.getAddressId();
    }

    /**
     * 주소 삭제
     * @param addressId
     * @return
     */
    @Transactional
    public Long deleteAddress(Long addressId) {
        //기본배송지는 삭제 불가능
        Member member = memberService.getLoginMember();

        Address findAddress = addressRepository.findById(addressId).orElseThrow(
                () -> new RuntimeException("주소가 존재하지 않습니다.")
        );

        if (!findAddress.getMember().getId().equals(member.getId())) {
            throw new RuntimeException("본인의 주소만 삭제할 수 있습니다.");
        }

        if (findAddress.isDefault()) {
            throw new RuntimeException("기본 배송지는 삭제 할 수 없습니다.");
        }

        addressRepository.delete(findAddress);
        return findAddress.getAddressId();
    }

    public List<AddressResponseDto> addresses() {
        Member member = memberService.getLoginMember();
        List<Address> addresses =
                addressRepository.findByMemberOrderByAddressIdDesc(member);

        List<AddressResponseDto> dtos = addresses.stream().map(
                address -> new AddressResponseDto(
                        address.getAddressId(),
                        address.getZoneCode(),
                        address.getRoadAddress(),
                        address.getDetailAddress(),
                        address.isDefault()
                )).toList();
        return dtos;
    }
}
