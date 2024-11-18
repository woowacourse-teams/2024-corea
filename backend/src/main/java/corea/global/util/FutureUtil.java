package corea.global.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class FutureUtil {

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    public static <T> CompletableFuture<T> supplyAsync(final Supplier<T> supplier, final Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }
}
