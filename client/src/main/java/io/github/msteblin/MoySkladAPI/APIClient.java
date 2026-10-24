package io.github.msteblin.MoySkladAPI;

public class APIClient {
    public final ProductAPI products;

    public APIClient(String msToken) {
        Core core = new Core(msToken);
        this.products = new ProductAPI(core);
    }
}
