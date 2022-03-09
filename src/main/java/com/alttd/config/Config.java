package com.alttd.config;

import java.util.HashMap;
import java.util.Map;

public final class Config extends AbstractConfig {

    static Config config;
    static int version;

    public Config() {
        super("config.yml");
    }

    public static void reload() {
        config = new Config();

        version = config.getInt("config-version", 1);
        config.set("config-version", 1);

        config.readConfig(Config.class, null);
    }

    public static Map<String, Boolean> WHITELIST_STATES = new HashMap<>();
    private static void loadState() {
        WHITELIST_STATES = config.getBooleanMap("whitelist.whitelist-state", WHITELIST_STATES);
    }

    public static void setWhitelist(String server, boolean state) {
        WHITELIST_STATES.put(server, state);
        config.set("whitelist.whitelist-state", WHITELIST_STATES);
    }


    public static String DEFAULT_SERVER = "lobby";
    public static void loadSettings() {
        DEFAULT_SERVER = config.getString("settings.default-server", DEFAULT_SERVER);
    }
}