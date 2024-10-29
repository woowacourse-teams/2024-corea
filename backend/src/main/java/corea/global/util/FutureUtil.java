package corea.global.util;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class FutureUtil {

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }
}
