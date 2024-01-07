package com.hilal.evaexchangeproject.controller;

import com.hilal.evaexchangeproject.dto.ShareDto;
import com.hilal.evaexchangeproject.service.ShareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.hilal.evaexchangeproject.constant.Endpoints.*;

@RestController
@RequestMapping(ROOT+SHARE)
public class ShareController {

    private final ShareService shareService;

    public ShareController(ShareService shareService) {
        this.shareService = shareService;
    }

    @PostMapping(CREATESHARE)
    public ResponseEntity<String> saveShare(
            @RequestParam String username,
            @RequestParam String symbol,
            @RequestParam BigDecimal purchasePrice,
            @RequestParam BigDecimal salePrice,
            @RequestParam Integer quantity) {

        String result = shareService.saveShare(username, symbol, purchasePrice, salePrice, quantity);

        return ResponseEntity.ok(result);
    }


    @GetMapping(GETALLSHARES)
    public ResponseEntity<List<ShareDto>> getAllShares() {
        try {
            List<ShareDto> shares = shareService.getAllShares();
            return ResponseEntity.ok(shares);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PostMapping(BUYSHARE)
    public ResponseEntity<String> buyShare(
            @RequestParam String username,
            @RequestParam String symbol,
            @RequestParam Integer purchaseQuantity) {
        try {
            String result = shareService.buyShare(username, symbol,  purchaseQuantity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during share purchase.");
        }
    }
    @PostMapping(SELLSHARE)
    public ResponseEntity<String> sellShare(
            @RequestParam String username,
            @RequestParam String symbol,
            @RequestParam Integer saleQuantity) {
        try {
            String result = shareService.sellShare(username, symbol, saleQuantity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during share sale.");
        }
    }

    @PutMapping("/updatePrices")
    public ResponseEntity<String> updateSharePrices(
            @RequestParam String username,
            @RequestParam String symbol,
            @RequestParam BigDecimal newPurchasePrice,
            @RequestParam BigDecimal newSalePrice) {
        try {
            String result = shareService.updateSharePrices(username, symbol, newPurchasePrice, newSalePrice);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during share price update.");
        }
    }

}
