package dearest.dearestshop.config;

import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.member.Role;
import dearest.dearestshop.domain.product.*;
import dearest.dearestshop.dto.addressdto.AddressCreateDto;
import dearest.dearestshop.repository.AddressRepository;
import dearest.dearestshop.repository.CategoryRepository;
import dearest.dearestshop.repository.MemberRepository;
import dearest.dearestshop.repository.ProductRepository;
import dearest.dearestshop.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitDataService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Transactional
    public void init() {
        System.out.println("Starting to inject init data...");

        /**
         * 카테고리 생성
         */
        String[] categories = {
                "TOPS",
                "SKIRTS",
                "PANTS",
                "DRESSES",
                "BAGS"
        };

        for (String category : categories) {
            if(!categoryRepository.existsByCategoryName(category))
            {categoryRepository.save(Category.createCategory(category));}
        }

        /**
         * 회원 1명, 관리자 1명 생성
         */
        Member admin = null;
        if(!memberRepository.existsByEmail("123@test.com")){
            Member member = Member.createMember("member1","123@test.com", passwordEncoder.encode("123"), "01012341234");
            memberRepository.save(member);
        }
        if(!memberRepository.existsByEmail("admin@test.com")){
            admin = Member.createMember("admin","admin@test.com", passwordEncoder.encode("admin"), "01099999999");
            admin.changeRole(Role.ADMIN);
            memberRepository.save(admin);
        }

        /**
         * 초기상품 생성
         */
        List<ProductImage> productImages = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            String fileName = "short_top_" + i + ".png";

            FileInfo fileinfo = FileInfo.createFileInfo(fileName, fileName, null, "/images/" + fileName);
            ProductImage productImage = ProductImage.createProductImage(ImageType.THUMBNAIL, 1, fileinfo);
            productImages.add(productImage);
        }


        Category categoryTops = categoryRepository.findByCategoryName("TOPS").orElseThrow();


        Product product1 = Product.createProduct("dear logo tee", "Fitted 실루엣\n 크롭기장\n dearest로고 핫픽스 \n 라운드 네크라인",19000,10, 0, List.of(ProductSize.S, ProductSize.M), List.of(productImages.get(0)), categoryTops);
        Product product2 = Product.createProduct("dear logo tee soft", "Fitted 실루엣\n 크롭기장\n dearest로고 핫픽스 \n 라운드 네크라인",19000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImages.get(1)), categoryTops);
        Product product3 = Product.createProduct("dear logo tee pink", "Fitted 실루엣\n 크롭기장\n dearest로고 핫픽스 \n 라운드 네크라인",19000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImages.get(2)), categoryTops);
        Product product4 = Product.createProduct("lace tee", "Fitted 실루엣\n 크롭기장\n 프릴 디테일&레이스 트리밍 \n 라운드 네크라인",22000,10, 0, List.of(ProductSize.S, ProductSize.M, ProductSize.L),List.of(productImages.get(3)), categoryTops);
        Product product5 = Product.createProduct("lace tee pink", "Fitted 실루엣\n 크롭기장\n 프릴 디테일&레이스 트리밍 \n 라운드 네크라인",22000,10, 0, List.of(ProductSize.S, ProductSize.M,ProductSize.L),List.of(productImages.get(4)), categoryTops);
        Product product6 = Product.createProduct("ribbon tee", "Fitted 실루엣\n 크롭기장\n 프릴 디테일&레이스 트리밍 \n 라운드 네크라인",20000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImages.get(5)), categoryTops);

        List<Product> products = List.of(product1,product2,product3,product4,product5,product6);
        productRepository.saveAll(products);


        addressRepository.save(Address.createAddress("21578","청계천로 111","101동 101호",true, admin));

        System.out.println("Init data injection completed");
    }

}
