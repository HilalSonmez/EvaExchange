package com.hilal.evaexchangeproject.converter;



import com.hilal.evaexchangeproject.dto.PortfolioDto;
import com.hilal.evaexchangeproject.entity.Portfolio;
import com.hilal.evaexchangeproject.entity.Share;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class PortfolioConverter {
    private final ShareConverter shareConverter;

    public PortfolioConverter(ShareConverter shareConverter) {
        this.shareConverter = shareConverter;
    }

    public PortfolioDto toDto(Portfolio portfolio) {
        return PortfolioDto.builder()
                .id(portfolio.getId())
                .userId(portfolio.getUser().getId())
                .userName(portfolio.getUser().getUsername())
                .shares(portfolio.getShares().stream()
                        .map(shareConverter::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public Portfolio toEntity(PortfolioDto portfolioDto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(portfolioDto.getId());

        List<Share> shares = portfolioDto.getShares().stream()
                .map(shareConverter::toEntity)
                .collect(Collectors.toList());
        portfolio.setShares(shares);
        return portfolio;
    }

}