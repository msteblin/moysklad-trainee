package io.github.msteblin.Marketplace.cartGood;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartGoodRepository extends JpaRepository<CartGood, CartGoodId> {
    @Query("SELECT cg FROM CartGood cg " +
            "JOIN FETCH cg.seller " +
            "WHERE cg.id.customerId = :customerId")
    List<CartGood> findAllByCustomerId(@Param("customerId") UUID customerId);
}
