package io.c0ded.worldeventcomms.client.util;

public record InWorldResult<T>(
        boolean success,
        T value,
        String error
) {
    public static <T> InWorldResult<T> ok(T value) {
        return new InWorldResult<>(true, value, null);
    }

    public static <T> InWorldResult<T> fail(String error) {
        return new InWorldResult<>(false, null, error);
    }
}