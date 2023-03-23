package by.ivankov.delivery.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "carts_products",
    joinColumns = @JoinColumn(name = "carts_id"),
    inverseJoinColumns = @JoinColumn(name = "products_id"))
    private Set<Product> productSet = new HashSet<>();

    public Cart() {
    }

    public Cart(Long id, User user, Set<Product> productSet) {
        this.id = id;
        this.user = user;
        this.productSet = productSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                ", productSet=" + productSet +
                '}';
    }
}
