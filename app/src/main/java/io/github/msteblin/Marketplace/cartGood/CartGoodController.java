package io.github.msteblin.Marketplace.cartGood;

import io.github.msteblin.Marketplace.cartGood.dto.CartGoodCreationDTO;
import io.github.msteblin.Marketplace.cartGood.dto.CartGoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers/{customerId}/cart")
@SuppressWarnings("unused")
public class CartGoodController {
    @Autowired
    private CartGoodService cartGoodService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartGoodDTO createCartGood(
            @RequestBody CartGoodCreationDTO cartGood,
            @PathVariable("customerId") UUID customerId
    ) {
        return cartGoodService.createCartGood(cartGood, customerId, cartGood.msGoodId());
    }

    @GetMapping("/{goodId}")
    public CartGoodDTO getCartGood(
            @PathVariable("customerId") UUID customerId,
            @PathVariable("goodId") UUID goodId
    ) {
        return cartGoodService.getCartGood(customerId, goodId);
    }

    @GetMapping
    public List<CartGoodDTO> getCustomerCartGoods(
            @PathVariable("customerId") UUID customerId
    ) {
        return cartGoodService.getCustomerCartGoods(customerId);
    }

    @DeleteMapping("/{goodId}")
    public void deleteCustomer(
            @PathVariable("customerId") UUID customerId,
            @PathVariable("goodId") UUID goodId
    ) {
        cartGoodService.deleteCartGood(customerId, goodId);
    }

    @PostMapping("/{goodId}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    // @ResponseStatus(HttpStatus.CREATED)
    public void processCartGood(
            @PathVariable("customerId") UUID customerId,
            @PathVariable("goodId") UUID goodId
    ) {
        cartGoodService.processCartGood(customerId, goodId);
    }
}
