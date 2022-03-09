package com.alttd;

import com.alttd.commands.CommandManager;
import com.alttd.config.Config;
import com.alttd.config.Messages;
import com.alttd.events.LoginEvent;
import com.alttd.util.Logger;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import java.io.File;
import java.nio.file.Path;

@Plugin(id = "permissionwhitelist", name = "PermissionWhitelist", version = "1.0.0",
        description = "A plugin that whitelists using permissions",
        authors = {"Teri"}
)
public class PermissionWhitelist {

    private static PermissionWhitelist instance;
    private final ProxyServer server;
    private final java.util.logging.Logger logger;
    private final Path dataDirectory;

    @Inject
    public PermissionWhitelist(ProxyServer proxyServer, java.util.logging.Logger proxyLogger, @DataDirectory Path proxydataDirectory) {
        server = proxyServer;
        instance = this;
        logger = proxyLogger;
        dataDirectory = proxydataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        new CommandManager();
        Config.reload();
        Messages.reload();
        Logger.info("--------------------------------------------------");
        Logger.info("PermissionWhitelist started");
        Logger.info("--------------------------------------------------");
        loadCommands();
        loadEvents();
    }

    private void loadCommands() {
        server.getCommandManager().register("permissionwhitelist", new CommandManager());
    }

    private void loadEvents() {
        server.getEventManager().register(this, new LoginEvent());
    }

    public static PermissionWhitelist getInstance() {
        return instance;
    }

    public ProxyServer getProxyServer() {
        return getInstance().server;
    }

    public java.util.logging.Logger getLogger() {
        return logger;
    }

    public File getDataFolder() {
        return dataDirectory.toFile();
    }
}