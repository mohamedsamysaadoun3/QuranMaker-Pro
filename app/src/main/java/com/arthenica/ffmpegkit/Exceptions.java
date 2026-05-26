package com.arthenica.ffmpegkit;

public class Exceptions {
    public static RuntimeException ArgIsNull(String message) {
        return new IllegalArgumentException(message);
    }
}
