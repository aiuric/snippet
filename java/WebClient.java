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
        Flux.zip(helloFlux, deviceCapabilityFlux).subscribeOn(Schedulers.elastic()).subscribe(dc -> {
                    System.out.println(dc.getT1());
                    System.out.println(dc.getT2().getPollRate());
                }
        );

        // 하나 call 한 결과를 이용해서 다른 call을 수행
        // callback hell 위험성 쓰지말자!!!
        helloFlux.subscribeOn(Schedulers.elastic()).subscribe(dc -> {
            Flux<DeviceCapability> deviceCapability2Flux = client.get()
                    .uri("/sep2/dcap")
                    .accept(MediaType.parseMediaType("application/sep+xml"))
                    .retrieve()
                    .bodyToFlux(DeviceCapability.class);

            deviceCapability2Flux.subscribeOn(Schedulers.elastic()).subscribe(dc2 -> System.out.println(dc2.getPollRate()));
        });
    }
    
    Flux<String> chainStartMethod() {
        try {
            Thread.sleep(5 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Flux.just(" calledChainStartMethod");
    }

    Flux<String> chainMethod(String tmp) {
        try {
            Thread.sleep(5 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Flux.just(tmp + " calledChainMethod");
    }
    
    // 재사용성이 없으니 lambda를 적극 사용할 것
    public void chainingTest() throws Exception {
        Flux<String> methodFlux = chainStartMethod();

        // lambda 사용
        Flux<String> deferFlux = Flux.defer(() -> {
            try {
                Thread.sleep(5 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Flux.just(" calledDeferFlux");
        });


        methodFlux.flatMap(ar -> chainMethod(ar)).flatMap(ar -> chainMethod(ar)).subscribeOn(Schedulers.elastic()).log().subscribe(dc -> {
            System.out.println(dc);
        });

        deferFlux.flatMap(ar -> chainMethod(ar)).flatMap(ar -> chainMethod(ar)).subscribeOn(Schedulers.elastic()).log().subscribe(dc -> {
            System.out.println(dc);
        });

        // lambda 사용
        deferFlux.flatMap(ar -> chainMethod(ar)).flatMap(ar -> {
            try {
                Thread.sleep(5 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Flux.just(ar + " calledChainMethod");
        }).subscribeOn(Schedulers.elastic()).log().subscribe(dc -> {
            System.out.println(dc);
        });
    }
}

/*
observer 패턴
observable (run() 구현) 에 obsever (update() 구현 -> 넘기는 데이터는 notifyObservers()의 인자로 전달)를 등록시키고 observable을 run()하면 등록된 observer의 update()를 call 하는 패턴
observable -> publisher(mono or flux)
obsever -> subscriber
*/
