package it.mc;

import reactor.core.publisher.Mono;

import static java.util.Objects.nonNull;

public class ThenBehavior {
    public Mono<String> get(String id, String token) {
        return authVerify(token)
                .then(Mono.defer(()-> retrieve(id)))
                //.then(retrieve(id))
                .doOnSuccess(System.out::println)
                .doOnError(System.err::println);
    }

    protected Mono<Void> authVerify(String token) {
        if (nonNull(token) && token.equals("invalidToken")) {
            return Mono.error(new RuntimeException("Invalid token"));
        }
        return Mono.empty();
    }

    protected Mono<String> retrieve(String id) {
        // stubbed implementation
        String value = "value";
        System.out.println(id + "->" + value);
        return Mono.just(value);
    }
}
