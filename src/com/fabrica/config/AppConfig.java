package com.fabrica.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppConfig {
    public static final String MONITOR_HOST = "127.0.0.1";
    public static final int MONITOR_PORT = 5050;
    public static final long MONITOR_INTERVAL_MS = 2_000L;
    public static final long MONITOR_SHUTDOWN_TIMEOUT_MS = 2_000L;

    public static final Path DATA_DIRECTORY = Paths.get("data");
    public static final Path SNAPSHOT_PATH = DATA_DIRECTORY.resolve("estoque-snapshot.tsv");
    public static final Path MOVEMENT_LOG_PATH = DATA_DIRECTORY.resolve("movimentacoes.log");
    public static final Path ALERT_LOG_PATH = DATA_DIRECTORY.resolve("alertas.log");

    private AppConfig() {
    }
}
