package com.hilal.evaexchangeproject.repository;


import com.hilal.evaexchangeproject.entity.Portfolio;
import com.hilal.evaexchangeproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByUser(User user);




}
