package com.hilal.evaexchangeproject.converter;



import com.hilal.evaexchangeproject.dto.ShareDto;
import com.hilal.evaexchangeproject.entity.Portfolio;
import com.hilal.evaexchangeproject.entity.Share;
import org.springframework.stereotype.Component;



@Component
public class ShareConverter {

    public ShareDto toDto(Share share) {
        return ShareDto.builder()
                .portfolioId(share.getPortfolio().getId())
                .symbol(share.getSymbol())
                .purchasePrice(share.getPurchasePrice())
                .salePrice(share.getSalePrice())
                .saleQuantity(share.getSaleQuantity())
                .purchaseQuantity(share.getPurchaseQuantity())
                .build();
    }

    public Share toEntity(ShareDto shareDto) {
        Share share = new Share();
        share.setSymbol(shareDto.getSymbol());
        share.setPurchasePrice(shareDto.getPurchasePrice());
        share.setSalePrice(shareDto.getSalePrice());
        share.setSaleQuantity(shareDto.getSaleQuantity());
        share.setPurchaseQuantity(shareDto.getPurchaseQuantity());

        if (shareDto.getPortfolioId() != null) {

            Portfolio portfolio = new Portfolio();
            portfolio.setId(shareDto.getPortfolioId());


            share.setPortfolio(portfolio);
        } else {

        }
        return share;
    }
    public void updateEntityFromDto(Share existingShare, ShareDto shareDto) {
        existingShare.setSymbol(shareDto.getSymbol());
        existingShare.setPurchasePrice(shareDto.getPurchasePrice());
        existingShare.setSalePrice(shareDto.getSalePrice());
        existingShare.setSaleQuantity(shareDto.getSaleQuantity());
        existingShare.setPurchaseQuantity(shareDto.getPurchaseQuantity());
    }
}
