package dearest.dearestshop.dto.orderdto;

import dearest.dearestshop.dto.addressdto.AddressCreateDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderCreateDto {

    private Long addressId;

    private String receiverName;

    private AddressCreateDto newAddress;

    private String deliveryMessage;

    private List<Long> cartItemIds;
}

