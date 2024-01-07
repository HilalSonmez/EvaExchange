package com.hilal.evaexchangeproject.service;


import com.hilal.evaexchangeproject.converter.PortfolioConverter;
import com.hilal.evaexchangeproject.converter.UserConverter;
import com.hilal.evaexchangeproject.dto.PortfolioDto;

import com.hilal.evaexchangeproject.dto.UserDto;
import com.hilal.evaexchangeproject.entity.Portfolio;

import com.hilal.evaexchangeproject.entity.User;
import com.hilal.evaexchangeproject.repository.PortfolioRepository;
import com.hilal.evaexchangeproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioConverter portfolioConverter;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public PortfolioService(PortfolioRepository portfolioRepository, PortfolioConverter portfolioConverter, UserService userService, UserRepository userRepository, UserConverter userConverter) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioConverter = portfolioConverter;
        this.userService = userService;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Transactional
    public String savePortfolio(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);

        if (existingUser.isEmpty()) {
            return "User not found with username: " + username;
        }

        User user = existingUser.get();

        Portfolio portfolio = Portfolio.builder()
                .user(user)
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // shareService.saveShare(username, shareDto);

        return "Portfolio saved successfully for user: " + username;
    }
    @Transactional(readOnly = true)
    public PortfolioDto getPortfolioByUsername(String username) {
        try {
            UserDto userDto = userService.getUserByUsername(username);

            if (userDto != null) {
                User user = userConverter.toEntity(userDto);

                Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUser(user);

                if (optionalPortfolio.isPresent()) {
                    Portfolio portfolio = optionalPortfolio.get();
                    return portfolioConverter.toDto(portfolio);
                } else {
                    throw new RuntimeException("Portfolio not found for user: " + username);
                }
            } else {
                throw new RuntimeException("User not found with username: " + username);
            }
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("An error occurred while getting portfolio for user: " + username);
        }
    }

        public List<PortfolioDto> getAllPortfolios () {

            List<Portfolio> portfolios = portfolioRepository.findAll();

            return portfolios.stream()
                    .map(portfolioConverter::toDto)
                    .collect(Collectors.toList());
        }

    }



