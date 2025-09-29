package com.ecommerce.config;

import com.ecommerce.util.CurrencyUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ThymeleafConfig {
    
    @Bean
    public CurrencyFormatter currencyFormatter() {
        return new CurrencyFormatter();
    }
    
    public static class CurrencyFormatter {
        public String formatINR(BigDecimal amount) {
            return CurrencyUtil.formatINR(amount);
        }
        
        public String formatINR(Double amount) {
            return CurrencyUtil.formatINR(amount);
        }
    }
}