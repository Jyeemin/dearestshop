package dearest.dearestshop.config;

import dearest.dearestshop.domain.Address;
import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.member.Role;
import dearest.dearestshop.domain.product.*;
import dearest.dearestshop.repository.AddressRepository;
import dearest.dearestshop.repository.CategoryRepository;
import dearest.dearestshop.repository.MemberRepository;
import dearest.dearestshop.repository.ProductRepository;
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
        List<ProductImage> productImagesTops = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            String fileName = "short_top_" + i + ".png";

            FileInfo fileinfo = FileInfo.createFileInfo(fileName, fileName, null, "/images/" + fileName);
            ProductImage productImage = ProductImage.createProductImage(ImageType.THUMBNAIL, 1, fileinfo);
            productImagesTops.add(productImage);
        }

        List<Category> findCategories = categoryRepository.findAll();

        Category categoryTops = findCategories.stream()
                .filter(category -> category.getCategoryName().equals("TOPS"))
                .findFirst()
                .orElseThrow();


        Product product1 = Product.createProduct("dear logo tee", "Fitted 실루엣\n 크롭기장\n dearest로고 핫픽스 \n 라운드 네크라인",19000,10, 0, List.of(ProductSize.S, ProductSize.M), List.of(productImagesTops.get(0)), categoryTops);
        Product product2 = Product.createProduct("dear logo tee soft", "Fitted 실루엣\n 크롭기장\n dearest로고 핫픽스 \n 라운드 네크라인",19000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImagesTops.get(1)), categoryTops);
        Product product3 = Product.createProduct("dear logo tee pink", "Fitted 실루엣\n 크롭기장\n dearest로고 핫픽스 \n 라운드 네크라인",19000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImagesTops.get(2)), categoryTops);
        Product product4 = Product.createProduct("lace tee", "Fitted 실루엣\n 크롭기장\n 프릴 디테일&레이스 트리밍 \n 라운드 네크라인",22000,10, 0, List.of(ProductSize.S, ProductSize.M, ProductSize.L),List.of(productImagesTops.get(3)), categoryTops);
        Product product5 = Product.createProduct("lace tee pink", "Fitted 실루엣\n 크롭기장\n 프릴 디테일&레이스 트리밍 \n 라운드 네크라인",22000,10, 0, List.of(ProductSize.S, ProductSize.M,ProductSize.L),List.of(productImagesTops.get(4)), categoryTops);
        Product product6 = Product.createProduct("ribbon tee", "Fitted 실루엣\n 크롭기장\n 프릴 디테일&레이스 트리밍 \n 라운드 네크라인",20000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImagesTops.get(5)), categoryTops);

        List<Product> productsTops = List.of(product1,product2,product3,product4,product5,product6);
        productRepository.saveAll(productsTops);

        /**
         * 원피스 초기상품 생성
         */

        List<ProductImage> productImagesDress = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            String fileName = "dress_" + i + ".png";

            FileInfo fileinfo = FileInfo.createFileInfo(fileName, fileName, null, "/images/" + fileName);
            ProductImage productImage = ProductImage.createProductImage(ImageType.THUMBNAIL, 1, fileinfo);
            productImagesDress.add(productImage);
        }

        Category categoryDresses = findCategories.stream()
                .filter(category -> category.getCategoryName().equals("DRESSES"))
                .findFirst()
                .orElseThrow();


        Product product_dress_1 = Product.createProduct("satin dress", "A라인 실루엣\n 미디 기장\n 조절 가능한 네크라인 & 힙 스트랩 \n 속치마 내장",106000,10, 0, List.of(ProductSize.S, ProductSize.M), List.of(productImagesDress.get(0)), categoryDresses);
        Product product_dress_2 = Product.createProduct("mini dress floral", "A라인 실루엣\n 미디 기장\n 조절 가능한 네크라인 & 힙 스트랩 \n 속치마 내장",89000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImagesDress.get(1)), categoryDresses);
        Product product_dress_3 = Product.createProduct("ivory sheering dress", "A라인 실루엣\n 미디 기장\n 조절 가능한 네크라인 & 힙 스트랩 \n 속치마 내장",112000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImagesDress.get(2)), categoryDresses);
        Product product_dress_4 = Product.createProduct("set-up dress black", "A라인 실루엣\n 미디 기장\n 프릴 디테일&레이스 트리밍 \n 속치마 내장",95000,10, 0, List.of(ProductSize.S, ProductSize.M, ProductSize.L),List.of(productImagesDress.get(3)), categoryDresses);
        Product product_dress_5 = Product.createProduct("two piece dress black", "A라인 실루엣\n 미디 기장\n 프릴 디테일&레이스 트리밍 \n 속치마 내장",158000,10, 0, List.of(ProductSize.S, ProductSize.M,ProductSize.L),List.of(productImagesDress.get(4)), categoryDresses);
        Product product_dress_6 = Product.createProduct("ribbon lace dress", "A라인 실루엣\n 미디 기장\n 프릴 디테일&레이스 트리밍 \n 속치마 내장",163000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImagesDress.get(5)), categoryDresses);

        List<Product> productsDresses = List.of(product_dress_1, product_dress_2, product_dress_3, product_dress_4, product_dress_5, product_dress_6);
        productRepository.saveAll(productsDresses);

        /**
         * 바지 초기상품 생성
         */

        List<ProductImage> productImagesPants = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String fileName = "pants_" + i + ".png";

            FileInfo fileinfo = FileInfo.createFileInfo(fileName, fileName, null, "/images/" + fileName);
            ProductImage productImage = ProductImage.createProductImage(ImageType.THUMBNAIL, 1, fileinfo);
            productImagesPants.add(productImage);
        }

        Category categoryPants = findCategories.stream()
                .filter(category -> category.getCategoryName().equals("PANTS"))
                .findFirst()
                .orElseThrow();


        Product product_pants_1 = Product.createProduct("cago pants white", "퓨어한 화이트 컬러의 로우라이즈 스트레이트 진\n 허벅지부터 밑단까지 자연스럽게 떨어지는 스트레이트 실루엣\n 광택 없는 퓨어 화이트 컬러로 청순한 무드를 연출",62000,10, 0, List.of(ProductSize.S, ProductSize.M), List.of(productImagesPants.get(0)), categoryPants);
        Product product_pants_2 = Product.createProduct("straight jeans", "자연스러운 워싱이 더해진 데님 컬러\n 길어보이는 다리라인을 연출하는 스트레이트 핏\n 데일리 룩에 활용하기 좋은 클린 데님 디자인",59000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImagesPants.get(1)), categoryPants);
        Product product_pants_3 = Product.createProduct("bootscut paints", "자연스러운 워싱이 더해진 데님 컬러\n 길어보이는 다리라인을 연출하는 스트레이트 핏\n 데일리 룩에 활용하기 좋은 클린 데님 디자인",67000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImagesPants.get(2)), categoryPants);
        Product product_pants_4 = Product.createProduct("sweatpants black", "자연스러운 워싱이 더해진 데님 컬러\n 길어보이는 다리라인을 연출하는 스트레이트 핏\n 데일리 룩에 활용하기 좋은 클린 데님 디자인",74000,10, 0, List.of(ProductSize.S, ProductSize.M, ProductSize.L),List.of(productImagesPants.get(3)), categoryPants);
        Product product_pants_5 = Product.createProduct("softly sweatpants pink", "내추럴한 스트레이트 실루엣의 로우라이즈 스웻 팬츠\n 얇은 웨이스트 라인과 스트링 조절로 편안한 착용감 선사",53000,10, 0, List.of(ProductSize.S, ProductSize.M,ProductSize.L),List.of(productImagesPants.get(4)), categoryPants);

        List<Product> productsPants = List.of(product_pants_1, product_pants_2, product_pants_3, product_pants_4, product_pants_5);
        productRepository.saveAll(productsPants);

        /**
         * 치마 초기상품 생성
         */

        List<ProductImage> productImagesSkirt = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String fileName = "skirt_" + i + ".png";

            FileInfo fileinfo = FileInfo.createFileInfo(fileName, fileName, null, "/images/" + fileName);
            ProductImage productImage = ProductImage.createProductImage(ImageType.THUMBNAIL, 1, fileinfo);
            productImagesSkirt.add(productImage);
        }

        Category categorySkirt = findCategories.stream()
                .filter(category -> category.getCategoryName().equals("SKIRTS"))
                .findFirst()
                .orElseThrow();


        Product product_skirt_1 = Product.createProduct("ballet lace skirt black", "허리 밴딩 + 레이스 디테일로 편안하고 안정적인 착용감\n 속팬츠 밑단에 더해진 레이스 트리밍 포인트\n 속바지 내장으로 활동 시 노출 부담 없이 착용 가능",39000,10, 0, List.of(ProductSize.S, ProductSize.M), List.of(productImagesSkirt.get(0)), categorySkirt);
        Product product_skirt_2 = Product.createProduct("ballet lace skirt pink", "허리 밴딩 + 레이스 디테일로 편안하고 안정적인 착용감\n 속팬츠 밑단에 더해진 레이스 트리밍 포인트\n 속바지 내장으로 활동 시 노출 부담 없이 착용 가능",39000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImagesSkirt.get(1)), categorySkirt);
        Product product_skirt_3 = Product.createProduct("ballet lace skirt white", "허리 밴딩 + 레이스 디테일로 편안하고 안정적인 착용감\n 속팬츠 밑단에 더해진 레이스 트리밍 포인트\n 속바지 내장으로 활동 시 노출 부담 없이 착용 가능",39000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImagesSkirt.get(2)), categorySkirt);
        Product product_skirt_4 = Product.createProduct("ballet lace skirt cream blue", "허리 밴딩 + 레이스 디테일로 편안하고 안정적인 착용감\n 속팬츠 밑단에 더해진 레이스 트리밍 포인트\n 속바지 내장으로 활동 시 노출 부담 없이 착용 가능",39000,10, 0, List.of(ProductSize.S, ProductSize.M),List.of(productImagesSkirt.get(3)), categorySkirt);

        List<Product> productsSkirt = List.of(product_skirt_1, product_skirt_2, product_skirt_3, product_skirt_4);
        productRepository.saveAll(productsSkirt);


        addressRepository.save(Address.createAddress("21578","청계천로 111","101동 101호",true, admin));

        System.out.println("Init data injection completed");
    }



}
