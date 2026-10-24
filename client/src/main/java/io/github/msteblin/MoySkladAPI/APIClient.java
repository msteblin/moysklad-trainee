package io.github.msteblin.MoySkladAPI;

public class APIClient {
    public final ProductAPI products;

    public APIClient() {
        Core core = new Core();
        this.products = new ProductAPI(core);
    }
}
