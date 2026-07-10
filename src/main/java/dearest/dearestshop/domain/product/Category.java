package dearest.dearestshop.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String category_name;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    //생성 메소드
    public static Category createCategory(String category_name){
        Category category = new Category();
        category.category_name = category_name;
        return category;
    }

    //편의 메소드
    public void addProduct(Product product){
        products.add(product);
    }
}
