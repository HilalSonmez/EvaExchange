package com.hilal.evaexchangeproject.controller;

import com.hilal.evaexchangeproject.dto.PortfolioDto;
import com.hilal.evaexchangeproject.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hilal.evaexchangeproject.constant.Endpoints.*;

@RestController
@RequestMapping(ROOT+PORTFOLIO)
public class PortfolioController {
    private final PortfolioService portfolioService;


    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping(CREATEPORTFOLIO)
    public String savePortfolio(@RequestParam String username) {
        return portfolioService.savePortfolio(username);
    }

    @GetMapping(GETPORTFOLIOBYUSERNAME)
    public ResponseEntity<PortfolioDto> getPortfolioByUsername(@RequestParam String username) {
        try {
            PortfolioDto portfolio = portfolioService.getPortfolioByUsername(username);
            return ResponseEntity.ok(portfolio);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }}
    @GetMapping(GETALLPORTFOLIOS)
    public ResponseEntity<List<PortfolioDto>> getAllPortfolios() {
        try {
            List<PortfolioDto> portfolios = portfolioService.getAllPortfolios();
            return ResponseEntity.ok(portfolios);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
