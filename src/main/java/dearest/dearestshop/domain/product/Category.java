package dearest.dearestshop.domain.product;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String category_name;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public Category(Long id, String category_name, List<Product> products) {
        this.id = id;
        this.category_name = category_name;
        this.products = products;
    }
}
