package io.github.msteblin.Marketplace.cartGood;

import io.github.msteblin.Marketplace.cartGood.dto.CartGoodCreationDTO;
import io.github.msteblin.Marketplace.cartGood.dto.CartGoodDTO;
import io.github.msteblin.Marketplace.customer.Customer;
import io.github.msteblin.Marketplace.exception.NotFoundException;
import io.github.msteblin.Marketplace.seller.Seller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CartGoodService {
    @Autowired
    @SuppressWarnings("unused")
    private CartGoodRepository cartGoodRepository;

    @Transactional
    public CartGoodDTO createCartGood(CartGoodCreationDTO dto, UUID customerId, UUID msGoodId) {
        return createCartGood(dto, new CartGoodId(customerId, msGoodId));
    }

    @Transactional
    public CartGoodDTO createCartGood(CartGoodCreationDTO dto, CartGoodId id) {
        CartGood cartGood = new CartGood(
                new Customer(id.getCustomerId()),
                id.getMsGoodId(),
                new Seller(dto.sellerId()),
                dto.quantity()
        );
        CartGood savedCartGood = cartGoodRepository.save(cartGood);
        log.debug("Cart good: {}, saved cart good: {}", cartGood, savedCartGood);
        log.info("Created cart good, id={}", savedCartGood.getId());
        return CartGoodDTO.fromCartGood(savedCartGood);
    }

    @Transactional(readOnly = true)
    public CartGoodDTO getCartGood(UUID customerId, UUID msGoodId) {
        return getCartGood(new CartGoodId(customerId, msGoodId));
    }

    @Transactional(readOnly = true)
    public CartGoodDTO getCartGood(CartGoodId id) {
        // TODO: commonize
        CartGood cartGood = cartGoodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("cart good id=" + id));
        log.debug("Cart good: {}", cartGood);
        log.info("Returning cart good, id={}", cartGood.getId());
        return CartGoodDTO.fromCartGood(cartGood);
    }

    @Transactional(readOnly = true)
    public List<CartGoodDTO> getCustomerCartGoods(UUID customerId) {
        List<CartGood> cartGoods = cartGoodRepository.findAllByCustomerId(customerId);
        log.debug("Cart goods: {}", cartGoods);
        log.info("Returning {} cart goods", cartGoods.size());
        return cartGoods.stream().map(CartGoodDTO::fromCartGood).toList();
    }

    @Transactional
    public void deleteCartGood(UUID customerId, UUID msGoodId) {
        deleteCartGood(new CartGoodId(customerId, msGoodId));
    }

    @Transactional
    public void deleteCartGood(CartGoodId id) {
        log.info("Deleting cart good, id={}", id);
        cartGoodRepository.deleteById(id);
    }

    // TODO: implement
    @Transactional
    public void processCartGood(UUID customerId, UUID msGoodId) {
        processCartGood(new CartGoodId(customerId, msGoodId));
    }

    @Transactional
    public void processCartGood(CartGoodId id) {
        // TODO: submit an order
        // deleteCartGood(id);
    }
}
