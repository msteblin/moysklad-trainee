package io.github.msteblin.Marketplace.seller;

import io.github.msteblin.Marketplace.account.Account;
import io.github.msteblin.Marketplace.exception.NotFoundException;
import io.github.msteblin.Marketplace.seller.dto.SellerCreationDTO;
import io.github.msteblin.Marketplace.seller.dto.SellerDTO;
import io.github.msteblin.MoySkladAPI.APIClient;
import io.github.msteblin.MoySkladAPI.types.Product;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
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
        return SellerDTO.fromSeller(savedSeller);
    }

    @Transactional
    public SellerDTO getSeller(UUID id) {
        // TODO: commonize
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("seller id=" + id));
        return SellerDTO.fromSeller(seller);
    }

    @Transactional
    public void deleteSeller(UUID id) {
        sellerRepository.deleteById(id);
    }

    // TODO: refactor goods -> products
    public List<Product> getGoods(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("seller id=" + sellerId));
        return apiProvider.getObject(seller.getMsToken()).products.getAll();
    }

    public Product getGood(UUID sellerId, UUID goodId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("seller id=" + sellerId));
        return apiProvider.getObject(seller.getMsToken()).products.get(goodId);
    }
}
