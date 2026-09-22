package dearest.dearestshop.dto.orderdto;

import dearest.dearestshop.dto.addressdto.AddressCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {

    private Long addressId;

    private String receiverName;

    private AddressCreateDto newAddress;

    private String deliveryMessage;

    private List<Long> cartItemIds;
}

