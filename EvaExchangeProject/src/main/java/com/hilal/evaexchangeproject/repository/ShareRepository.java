package com.hilal.evaexchangeproject.repository;

import com.hilal.evaexchangeproject.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share,Long> {
    Share findBySymbol(String symbol);

    List<Share> findAllBySymbol(String symbol);

    @Modifying
    @Query("UPDATE Share s SET s.purchaseQuantity = s.purchaseQuantity + :quantity WHERE s.symbol = :symbol")
    void updatePurchaseQuantity(@Param("symbol") String symbol, @Param("quantity") Integer quantity);
}
