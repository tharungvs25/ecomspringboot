package com.ecommerce.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtil {
    
    private static final NumberFormat INR_FORMAT = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##,###.00");
    
    /**
     * Format BigDecimal amount as INR currency with rupee symbol
     * @param amount the amount to format
     * @return formatted string like "₹1,23,456.00"
     */
    public static String formatINR(BigDecimal amount) {
        if (amount == null) {
            return "₹0.00";
        }
        return "₹" + DECIMAL_FORMAT.format(amount);
    }
    
    /**
     * Format BigDecimal amount as INR currency using Java's built-in formatter
     * @param amount the amount to format
     * @return formatted string using Indian locale
     */
    public static String formatINRWithLocale(BigDecimal amount) {
        if (amount == null) {
            return INR_FORMAT.format(0);
        }
        return INR_FORMAT.format(amount);
    }
    
    /**
     * Format double amount as INR currency
     * @param amount the amount to format
     * @return formatted string like "₹1,23,456.00"
     */
    public static String formatINR(double amount) {
        return formatINR(BigDecimal.valueOf(amount));
    }
}