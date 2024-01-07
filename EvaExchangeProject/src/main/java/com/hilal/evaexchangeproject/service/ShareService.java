package com.hilal.evaexchangeproject.service;


import com.hilal.evaexchangeproject.converter.ShareConverter;
import com.hilal.evaexchangeproject.dto.PortfolioDto;
import com.hilal.evaexchangeproject.dto.ShareDto;
import com.hilal.evaexchangeproject.dto.UserDto;
import com.hilal.evaexchangeproject.entity.Portfolio;
import com.hilal.evaexchangeproject.entity.Share;
import com.hilal.evaexchangeproject.entity.User;
import com.hilal.evaexchangeproject.repository.PortfolioRepository;
import com.hilal.evaexchangeproject.repository.ShareRepository;
import com.hilal.evaexchangeproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShareService {
    private final ShareRepository shareRepository;
    private final UserService userService;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    private final PortfolioService portfolioService;
    private final ShareConverter shareConverter;



    public ShareService(ShareRepository shareRepository, UserService userService,PortfolioRepository portfolioRepository, UserRepository userRepository,PortfolioService portfolioService, ShareConverter shareConverter) {
        this.shareRepository = shareRepository;
        this.userService = userService;
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
        this.portfolioService = portfolioService;
        this.shareConverter = shareConverter;
    }

    @Transactional
    public String saveShare(String username, String symbol, BigDecimal purchasePrice, BigDecimal salePrice, Integer quantity) {
        try {

            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isEmpty()) {
                return "User not found with username: " + username + ". Cannot save the share.";
            }

            User user = optionalUser.get();

            Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUser(user);

            if (optionalPortfolio.isEmpty()) {
                return "User does not have a portfolio. Cannot save the share.";
            }

            Portfolio userPortfolio = optionalPortfolio.get();

            Share newShare = new Share();
            newShare.setSymbol(symbol);
            newShare.setPurchasePrice(purchasePrice);
            newShare.setSalePrice(salePrice);
            newShare.setQuantity(quantity);


            newShare.setPortfolio(userPortfolio);

            if (newShare.getSymbol() == null || newShare.getSymbol().isEmpty()) {
                return "Symbol cannot be null or empty. Cannot save the share.";
            }

            if (newShare.getQuantity() == null) {
                newShare.setQuantity(0);
            }


            shareRepository.save(newShare);

            return "Share saved successfully for user: " + username;
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while saving the share for user: " + username;
        }

    }




    @Transactional
    public String buyShare(String username, String symbol, Integer purchaseQuantity) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isEmpty()) {
                return "User not found with username: " + username + ". Cannot save the share.";
            }

            User user = optionalUser.get();

            Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUser(user);

            if (optionalPortfolio.isEmpty()) {
                return "User does not have a portfolio. Cannot save the share.";
            }

            Portfolio userPortfolio = optionalPortfolio.get();


            Share existingShare = shareRepository.findBySymbol(symbol.toUpperCase());

            if (existingShare == null) {
                return "Share not found with symbol: " + symbol;
            }


            ShareDto shareDto = shareConverter.toDto(existingShare);


            Share share = shareConverter.toEntity(shareDto);


            share.setPurchaseQuantity(purchaseQuantity);

            updateShareQuantity(symbol, purchaseQuantity);



            return "Share bought successfully for user: " + username;
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred during share purchase.";
        }
    }


    @Transactional
    public String updateShareQuantity(String symbol, Integer purchaseQuantity) {
        try {
            Share existingShare = shareRepository.findBySymbol(symbol);

            if (existingShare == null) {
                return "Share not found with symbol: " + symbol;
            }


            Integer updatedQuantity = existingShare.getQuantity() + purchaseQuantity;


            existingShare.setQuantity(updatedQuantity);


            shareRepository.save(existingShare);

            return "Share quantity updated successfully for symbol: " + symbol;
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred during share quantity update.";
        }
    }

    @Transactional(readOnly = true)
    public List<ShareDto> getAllShares() {
        List<Share> shares = shareRepository.findAll();
        return shares.stream()
                .map(shareConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public String sellShare(String username, String symbol, Integer saleQuantity) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isEmpty()) {
                return "User not found with username: " + username + ". Cannot save the share.";
            }

            User user = optionalUser.get();

            Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUser(user);

            if (optionalPortfolio.isEmpty()) {
                return "User does not have a portfolio. Cannot save the share.";
            }

            Portfolio userPortfolio = optionalPortfolio.get();


            Share existingShare = shareRepository.findBySymbol(symbol.toUpperCase());

            if (existingShare == null) {
                return "Share not found with symbol: " + symbol;
            }

            if (!userPortfolio.getShares().contains(existingShare)) {
                return "User does not own the share with symbol: " + symbol;
            }

            if (existingShare.getQuantity() < saleQuantity) {
                return "User does not own enough quantity of share with symbol: " + symbol;
            }


            ShareDto updatedShareDto = shareConverter.toDto(existingShare);
            updatedShareDto.setSaleQuantity(updatedShareDto.getSaleQuantity() + saleQuantity);

            shareConverter.updateEntityFromDto(existingShare, updatedShareDto);


            existingShare.setQuantity(existingShare.getQuantity() - saleQuantity);

            if (existingShare.getQuantity() == 0) {
                userPortfolio.getShares().remove(existingShare);
            }


            return "Share sold successfully for user: " + username;
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred during share sale.";
        }
    }
        @Transactional
        public String updateSharePrices(String username, String symbol, BigDecimal newPurchasePrice, BigDecimal newSalePrice) {
            try {

                UserDto user = userService.getUserByUsername(username);

                if (user == null) {
                    return "User not found with username: " + username;
                }


                PortfolioDto userPortfolio = portfolioService.getPortfolioByUsername(username);


                List<Share> sharesToUpdate = shareRepository.findAllBySymbol(symbol.toUpperCase());

                if (sharesToUpdate.isEmpty()) {
                    return "No shares found with symbol: " + symbol;
                }


                sharesToUpdate.forEach(share -> {
                    share.setPurchasePrice(newPurchasePrice);
                    share.setSalePrice(newSalePrice);
                    shareRepository.save(share);
                });



                return "Share prices updated successfully for symbol: " + symbol;
            } catch (Exception e) {
                e.printStackTrace();
                return "An error occurred during share price update.";
            }
        }
}