package io.c0ded.worldeventcomms.client.util;

public record ScoreboardResult<T>(
        boolean success,
        T value,
        String error
) {
    public static <T> ScoreboardResult<T> ok(T value) {
        return new ScoreboardResult<>(true, value, null);
    }

    public static <T> ScoreboardResult<T> fail(String error) {
        return new ScoreboardResult<>(false, null, error);
    }
}