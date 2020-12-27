import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AllOfTest {

    private String buildMessage() {
        try {
            Thread.sleep(5 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Completed!!";
    }

    public void allOfTest() throws Exception {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(this::buildMessage);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(this::buildMessage);
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(this::buildMessage);

        List<CompletableFuture<String>> completableFutures = Arrays.asList(cf1, cf2, cf3);

        CompletableFuture
                .allOf(completableFutures.toArray(new CompletableFuture[3]))
                .thenApplyAsync(result -> completableFutures.stream().map(future -> future.join()).collect(Collectors.toList()))
                //.thenAcceptAsync(messages -> messages.forEach(message -> System.out.println(message)));
                .thenAcceptAsync(messages -> messages.forEach(System.out::println)); // static method reference

        /*
        .thenRun : Runnable 을 파라미터로 하여, 정상실행후 CompletableFuture 리턴, 다음스테이지 실행에 반환값 없음
        .supplyAsync : Supplier 을 파라미터로 하며, 정상실행후  CompletableFuture 리턴, 다음스테이로 반환값 전달
        .thenAccept(Async) : Consumer 형태의 파이프라인 실행 , 인자가 Consumer 이기때문에 다음 스테이지로 결과값을 넘겨줄수 없다.
        .thenApply(Async)  : Function 형태의 파이프라인 실행, 인자가 Function 이기때문에 다음 스테이지로 결과값을 넘겨줄수 있다.
        .thenCompose : CompletableFuture 를 반환하는 Method Chain으로 실행하고자 할때.
        .exceptionally : 예외 사항 처리

        .allOf : 동시에 N개의 요청을 호출하고 나서 모든 스테이지가 완료되면 다음 스테이지를 실행한다.
        .anyOf : 동시에 N개의 요청을 호출하고 나서 하나라도 호출이 완료되면 다음 스테이지를 실행한다.
         */
        
        
        /* method reference */
        // 1. static method reference (인자 -> 인자로 추론)
        //.thenAcceptAsync(messages -> messages.forEach(message -> System.out.println(message)));
        .thenAcceptAsync(messages -> messages.forEach(System.out::println)); // static method reference
        
        // 2.1 instance method reference (인자 -> 인자로 추론)
        //CompletableFuture.supplyAsync(this::buildMessage);
        CompletableFuture.supplyAsync(() -> this.buildMessage());
        
        // 2.2 instance method reference (인자 -> 참조로 추론)
        //CompletableFuture.supplyAsync((arg0, rest) -> arg0.instanceMethod(rest));
        CompletableFuture.supplyAsync(ClassName::instanceMethod);
        
        // 3. constuctor reference (인자 -> 참조로 추론)
        //CompletableFuture.supplyAsync((arg0) -> new Class(arg0);
        CompletableFuture.supplyAsync(Class::new);        
    }
}
