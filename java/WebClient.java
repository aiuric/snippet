package com.qcells.ieeeconnector.client;

import com.qcells.ieeeconnector.dto.DeviceCapability;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class CommonClient {
    WebClient client = WebClient.create("http://localhost:8097");

    public void consume() {

        Flux<String> helloFlux = client.get()
                .uri("/hello")
                .retrieve()
                .bodyToFlux(String.class);

        Flux<DeviceCapability> deviceCapabilityFlux = client.get()
                .uri("/sep2/dcap")
                .accept(MediaType.parseMediaType("application/sep+xml"))
                .retrieve()
                .bodyToFlux(DeviceCapability.class);

        // multi call 모두 response 된 후 처리
        Flux.zip(helloFlux, deviceCapabilityFlux).subscribe(dc -> {
                    System.out.println(dc.getT1());
                    System.out.println(dc.getT2().getPollRate());
                }
        );

        // 하나 call 한 결과를 이용해서 다른 call을 수행
        helloFlux.subscribe(dc -> {
            Flux<DeviceCapability> deviceCapability2Flux = client.get()
                    .uri("/sep2/dcap")
                    .accept(MediaType.parseMediaType("application/sep+xml"))
                    .retrieve()
                    .bodyToFlux(DeviceCapability.class);

            deviceCapability2Flux.subscribe(dc2 -> System.out.println(dc2.getPollRate()));
        });

    }
}

/*
observer 패턴
observable (run() 구현) 에 obsever (update() 구현 -> 넘기는 데이터는 notifyObservers()의 인자로 전달)를 등록시키고 observable을 run()하면 등록된 observer의 update()를 call 하는 패턴
observable -> publisher(mono or flux)
obsever -> subscriber
*/
