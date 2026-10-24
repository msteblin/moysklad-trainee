package io.github.msteblin.MoySkladAPI;

import org.springframework.http.client.ReactorClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

public class Core {
    public static final String MS_API_URL = "https://api.moysklad.ru/api/remap/1.2";
    public final RestClient restClient;

    public Core() {
        this.restClient = RestClient.builder()
                .requestFactory(new ReactorClientHttpRequestFactory())
                .baseUrl(MS_API_URL).build();
    }
}
