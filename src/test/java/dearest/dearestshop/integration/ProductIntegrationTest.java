package dearest.dearestshop.integration;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.domain.product.*;
import dearest.dearestshop.domain.wishlist.Wishlist;
import dearest.dearestshop.domain.wishlist.WishlistItem;
import dearest.dearestshop.dto.productdto.*;
import dearest.dearestshop.repository.*;
import dearest.dearestshop.service.MemberService;
import dearest.dearestshop.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static dearest.dearestshop.domain.wishlist.QWishlistItem.wishlistItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ProductIntegrationTest {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    WishlistItemRepository wishlistItemRepository;


    @Autowired
    MemberRepository memberRepository;

    private Category category;
    private ProductCreateDto dto;
    private Product product;
    private Member member;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
         category = Category.createCategory("TOPS");
        categoryRepository.save(category);

        dto = new ProductCreateDto("반팔 티셔츠", "상품 설명", 30000, 10, List.of(ProductSize.S, ProductSize.M), category.getId());

        FileInfo fileInfo =
                FileInfo.createFileInfo(
                        "test.png",
                        "test.png",
                        100L,
                        "/images/test.png"
                );

        ProductImage thumbnail =
                ProductImage.createProductImage(
                        ImageType.THUMBNAIL,
                        0,
                        fileInfo
                );


        product =
                Product.createProduct(
                        "반팔 티셔츠",
                        "상품 설명",
                        30000,
                        10,
                        0,
                        List.of(ProductSize.S, ProductSize.M),
                        List.of(thumbnail),
                        category
                );

        productRepository.save(product);

        // 1. 회원 생성
        member = Member.createMember(
                "홍길동",
                "test@test.com",
                "encodedPassword",
                "01012341234"
        );

        // 2. 테스트용 로그인 회원 설정
        authentication =
                new UsernamePasswordAuthenticationToken(
                        member.getEmail(),
                        null,
                        List.of()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        memberRepository.save(member);
    }

    @Test
    public void 상품등록_성공() throws Exception{
    //given

        // 테스트용 이미지 생성
        MockMultipartFile image =
                new MockMultipartFile(
                        "images",
                        "test.png",
                        "image/png",
                        "test image".getBytes()
                );

        ProductInfoDto imageInfo = new ProductInfoDto(ImageType.THUMBNAIL, 0);

        //when
        Long productId =
                productService.createProduct(
                        dto,
                        List.of(image),
                        List.of(imageInfo)
                );

    //then
        Product findProduct =
                productRepository.findById(productId)
                        .orElseThrow();

        assertThat(findProduct.getProductName())
                .isEqualTo("셔츠");

        assertThat(findProduct.getPrice())
                .isEqualTo(30000);

        assertThat(findProduct.getStockQuantity())
                .isEqualTo(10);

        assertThat(findProduct.getSalesCount())
                .isEqualTo(0);

        assertThat(findProduct.getCategory())
                .isEqualTo(category);

        assertThat(findProduct.getImages())
                .hasSize(1);
    }


    @Test
    public void 이미지_이미지정보개수_불일치_상품등록실패() throws Exception{
    //given
        MockMultipartFile image =
                new MockMultipartFile(
                        "images",
                        "test.png",
                        "image/png",
                        "test".getBytes()
                );
        //when
        ProductInfoDto imageInfo1 = new ProductInfoDto(ImageType.THUMBNAIL, 0);
        ProductInfoDto imageInfo2 = new ProductInfoDto(ImageType.DETAIL, 0);
        //then
        assertThrows(RuntimeException.class,
                () -> productService.createProduct(dto,
                        List.of(image),
                        List.of(imageInfo1,imageInfo2)));

    }

    @Test
    public void 존재하지않는_카테고리() throws Exception{
    //given
     dto.setCategoryId(9999L);
    //when

    //then

                assertThrows(RuntimeException.class,
                        () -> productService.createProduct(dto,
                                List.of(),
                                List.of()));
    }

    @Test
    public void 상품조회_성공() throws Exception{
    //given
    productRepository.save(product);
    //when
        Product findProduct = productRepository.findById(product.getId()).orElseThrow();
        ProductDetailResponseDto result =
                productService.findOne(product.getId());
        //then
        assertThat(findProduct.getProductName()).isEqualTo("반팔 티셔츠");
        assertThat(findProduct.getPrice()).isEqualTo(30000);
        assertThat(findProduct.getStockQuantity()).isEqualTo(10);

        assertThat(result.getProductName())
                .isEqualTo("반팔 티셔츠");

        assertThat(result.getPrice())
                .isEqualTo(30000);
    }

    @Test
    public void 카테고리_조회() throws Exception{
        List<CategoryResponseDto> result =
                productService.findCategory();

        assertThat(result)
                .hasSize(1);
    }

    @Test
    public void 상품목록_조회() throws Exception{
    //given

    //when

        List<ProductResponseDto> result = productService.findAll();
        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProductName()).isEqualTo("반팔 티셔츠");
        assertThat(result.get(0).getPrice()).isEqualTo(30000);
    }


    @Test
    public void 상품목록_조회_위시리스트() throws Exception{
    //given
        FileInfo fileInfo1 =
                FileInfo.createFileInfo(
                        "test.png",
                        "test.png",
                        100L,
                        "/images/test1.png"
                );

        ProductImage thumbnail1 =
                ProductImage.createProductImage(
                        ImageType.THUMBNAIL,
                        0,
                        fileInfo1
                );


        Product product1 =
                Product.createProduct(
                        "반팔 티셔츠",
                        "귀여운 반팔 티셔츠",
                        30000,
                        10,
                        0,
                        List.of(ProductSize.S, ProductSize.M),
                        List.of(thumbnail1),
                        category
                );



        FileInfo fileInfo2 =
                FileInfo.createFileInfo(
                        "test2.png",
                        "test2.png",
                        100L,
                        "/images/test2.png"
                );

        ProductImage thumbnail2 =
                ProductImage.createProductImage(
                        ImageType.THUMBNAIL,
                        0,
                        fileInfo2
                );


        Product product2 =
                Product.createProduct(
                        "긴팔 셔츠",
                        "기본 긴팔 셔츠",
                        40000,
                        20,
                        0,
                        List.of(ProductSize.S, ProductSize.M),
                        List.of(thumbnail2),
                        category
                );

        productRepository.save(product1);
        productRepository.save(product2);

        Wishlist wishlist = Wishlist.createWishlist(member);
        wishlistRepository.save(wishlist);

        WishlistItem wishlistItem = WishlistItem.createWishlistItem(wishlist, product1);
        wishlist.addWishlistItem(wishlistItem);

        wishlistItemRepository.save(wishlistItem);
        //when
        List<ProductResponseDto> result = productService.findAll();


        //then
        assertThat(result).hasSize(3);

        ProductResponseDto result1 = result.stream().filter(dto -> dto.getProductId().equals(product1.getId())).findFirst()
                .orElseThrow();

        assertThat(result1.getProductName()).isEqualTo("반팔 티셔츠");
        assertThat(result1.getPrice()).isEqualTo(30000);
        assertThat(result1.getThumbnail()).isEqualTo("/images/test1.png");
        assertThat(result1.isWishlist()).isTrue();

        ProductResponseDto result2 = result.stream().filter(dto -> dto.getProductId().equals(product2.getId())).findFirst().orElseThrow();

        assertThat(result2.isWishlist()).isFalse();

        SecurityContextHolder.clearContext();
    }

    @Test
    public void 상품검색_1() throws Exception{
    //given
        // ==========================================
        // 2. 상품 1 생성
        // ==========================================

        FileInfo fileInfo1 =
                FileInfo.createFileInfo(
                        "top1.png",
                        "top1.png",
                        100L,
                        "/images/top1.png"
                );

        ProductImage thumbnail1 =
                ProductImage.createProductImage(
                        ImageType.THUMBNAIL,
                        0,
                        fileInfo1
                );

        Product product1 =
                Product.createProduct(
                        "반팔 티셔츠",
                        "귀여운 반팔 티셔츠",
                        30000,
                        10,
                        0,
                        List.of(ProductSize.S, ProductSize.M),
                        List.of(thumbnail1),
                        category
                );

        productRepository.save(product1);


        // ==========================================
        // 3. 상품 2 생성
        // ==========================================

        FileInfo fileInfo2 =
                FileInfo.createFileInfo(
                        "pants.png",
                        "pants.png",
                        100L,
                        "/images/pants.png"
                );

        ProductImage thumbnail2 =
                ProductImage.createProductImage(
                        ImageType.THUMBNAIL,
                        0,
                        fileInfo2
                );

        Product product2 =
                Product.createProduct(
                        "와이드 팬츠",
                        "편안한 와이드 팬츠",
                        50000,
                        20,
                        0,
                        List.of(ProductSize.M, ProductSize.L),
                        List.of(thumbnail2),
                        category
                );

        productRepository.save(product2);

        //when
        List<ProductResponseDto> result = productService.search("티셔츠", null, null);

    //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getProductName()).isEqualTo("반팔 티셔츠");
        assertThat(result.get(0).isWishlist()).isFalse();

        SecurityContextHolder.clearContext();
    }


    }

































