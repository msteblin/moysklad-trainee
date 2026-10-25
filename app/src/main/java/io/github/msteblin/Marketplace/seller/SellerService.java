package io.github.msteblin.Marketplace.seller;

import io.github.msteblin.Marketplace.account.Account;
import io.github.msteblin.Marketplace.exception.NotFoundException;
import io.github.msteblin.Marketplace.seller.dto.SellerCreationDTO;
import io.github.msteblin.Marketplace.seller.dto.SellerDTO;
import io.github.msteblin.MoySkladAPI.APIClient;
import io.github.msteblin.MoySkladAPI.types.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SellerService {
    @Autowired
    @SuppressWarnings("unused")
    private SellerRepository sellerRepository;

    @Autowired
    @SuppressWarnings("unused")
    private ObjectProvider<APIClient> apiProvider;

    // TODO: create an account automatically
    @Transactional
    public SellerDTO createSeller(SellerCreationDTO sellerCreationDTO) {
        // TODO: add token encryption
        Seller seller = new Seller(new Account(sellerCreationDTO.accountId()), sellerCreationDTO.msToken());
        Seller savedSeller = sellerRepository.save(seller);
        log.info("Created seller, id={}", savedSeller.getId());
        return SellerDTO.fromSeller(savedSeller);
    }

    @Transactional
    public SellerDTO getSeller(UUID id) {
        // TODO: commonize
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("seller id=" + id));
        // TODO: add request id
        log.info("Returned seller, id={}", seller.getId());
        return SellerDTO.fromSeller(seller);
    }

    @Transactional
    public void deleteSeller(UUID id) {
        sellerRepository.deleteById(id);
        log.info("Deleted seller, id={}", id);
    }

    // TODO: refactor goods -> products
    public List<Product> getGoods(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("seller id=" + sellerId));
        List<Product> goods = apiProvider.getObject(seller.getMsToken()).products.getAll();
        log.info("Returned {} good(s) of seller, id={}", goods.size(), seller.getId());
        return goods;
    }

    public Product getGood(UUID sellerId, UUID goodId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("seller id=" + sellerId));
        Product good = apiProvider.getObject(seller.getMsToken()).products.get(goodId);
        log.info("Returned good of seller, good id={}, seller id={}", good.id(), seller.getId());
        return good;
    }
}
