package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

@Component
public class ProductSpecification {
    public Specification<Product> build(ProductParamsDTO params) {
        return withTitle(params.getTitleCont())
                .and(withCategoryId(params.getCategoryId()))
                .and(withLessPrice(params.getPriceLt()))
                .and(withGreaterPrice(params.getPriceGt()))
                .and(withGreaterRating(params.getRatingGt()));
    }

    private Specification<Product> withTitle(String title) {
        return (root, query, cb) -> title == null ? cb.conjunction() : cb.like(root.get("title"), title);
    }

    private Specification<Product> withCategoryId(Long CategoryId) {
        return (root, query, cb) -> CategoryId == null ? cb.conjunction() : cb.equal(root.get("category").get("id"), CategoryId);
    }

    private Specification<Product> withLessPrice(Integer price) {
        return (root, query, cb) -> price == null ? cb.conjunction() : cb.lessThan(root.get("price"), price);
    }

    private Specification<Product> withGreaterPrice(Integer price) {
        return (root, query, cb) -> price == null ? cb.conjunction() : cb.greaterThan(root.get("price"), price);
    }

    private Specification<Product> withGreaterRating(Double rating) {
        return (root, query, cb) -> rating == null ? cb.conjunction() : cb.greaterThan(root.get("rating"), rating);
    }

}