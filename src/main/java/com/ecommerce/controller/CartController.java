package com.ecommerce.controller;

import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.CartService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewCart(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(authentication.getName()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        List<CartItem> cartItems = cartService.getCartItems(user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartService.getCartTotal(user));
        
        return "cart/view";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                           @RequestParam(defaultValue = "1") int quantity,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(authentication.getName()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        Optional<Product> productOpt = productService.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            
            if (!product.isActive()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Product is not available.");
                return "redirect:/products/" + productId;
            }
            
            if (!productService.isInStock(product, quantity)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Not enough stock available.");
                return "redirect:/products/" + productId;
            }

            cartService.addToCart(user, product, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Product added to cart!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found.");
        }

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam Long cartItemId,
                            @RequestParam int quantity,
                            RedirectAttributes redirectAttributes) {
        
        try {
            cartService.updateCartItem(cartItemId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Cart updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating cart.");
        }

        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId,
                                RedirectAttributes redirectAttributes) {
        
        try {
            cartService.removeFromCart(cartItemId);
            redirectAttributes.addFlashAttribute("successMessage", "Item removed from cart!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error removing item from cart.");
        }

        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(authentication.getName()).orElse(null);
        if (user != null) {
            cartService.clearCart(user);
            redirectAttributes.addFlashAttribute("successMessage", "Cart cleared!");
        }

        return "redirect:/cart";
    }
}