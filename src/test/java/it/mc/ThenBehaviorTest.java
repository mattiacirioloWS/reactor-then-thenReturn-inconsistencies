package it.mc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class ThenBehaviorTest {
    private final ThenBehavior thenBehavior = spy(new ThenBehavior());

    private static Stream<String> validTokens() {
        return Stream.of(null, "validToken", "another");
    }

    @ParameterizedTest
    @MethodSource("validTokens")
    public void validToken(String token) {
        StepVerifier.create(thenBehavior.get("123", token))
                .expectNext("value")
                .verifyComplete();
        verify(thenBehavior).authVerify(token);
        verify(thenBehavior).retrieve("123");
    }

    @Test
    public void invalidToken() {
        String invalidToken = "invalidToken";
        StepVerifier.create(thenBehavior.get("123", invalidToken))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && throwable.getMessage().equals("Invalid token"))
                .verify();
        verify(thenBehavior).authVerify(invalidToken);
        verify(thenBehavior, never()).retrieve("123");
    }

}