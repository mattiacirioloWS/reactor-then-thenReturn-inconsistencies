package it.mc;

import reactor.core.publisher.Mono;

import static java.util.Objects.nonNull;

public class ThenReturnBehavior {
    public Mono<String> get(String id, String token) {
        return authVerify(token)
                .thenReturn(retrieve(id))
                .doOnNext(System.out::println)
                .doOnError(System.err::println);
    }

    protected Mono<Void> authVerify(String token) {
        if (nonNull(token) && token.equals("invalidToken")) {
            return Mono.error(new RuntimeException("Invalid token"));
        }
        return Mono.empty();
    }

    protected String retrieve(String id) {
        // stubbed implementation
        String value = "value";
        System.out.println(id + "->" + value);
        return value;
    }
}
