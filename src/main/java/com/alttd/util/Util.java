package com.alttd.util;

import com.alttd.PermissionWhitelist;
import com.alttd.config.Config;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;

import java.util.Optional;

public class Util {
    public static void enforceWhitelistForServer(String serverName, RegisteredServer server, Component kickMessage) {
        Optional<RegisteredServer> optionalDefaultServer = PermissionWhitelist.getInstance().getProxyServer().getServer(Config.DEFAULT_SERVER);

        if (optionalDefaultServer.isEmpty() || optionalDefaultServer.get().equals(server)) {
            Logger.warning("Unable to find default server or enforcing whitelist on default server, kicking all players without permission to be here.");
            server.getPlayersConnected().stream()
                    .filter(player -> !player.hasPermission("permissionwhitelist.join.all") && !player.hasPermission("permissionwhitelist.join." + serverName))
                    .forEach(player -> player.disconnect(kickMessage));
            return;
        }

        RegisteredServer defaultServer = optionalDefaultServer.get();
        server.getPlayersConnected().stream()
                .filter(player -> !player.hasPermission("permissionwhitelist.join.all") && !player.hasPermission("permissionwhitelist.join." + serverName))
                .forEach(player -> {
                    player.createConnectionRequest(defaultServer);
                    player.sendMessage(kickMessage);
                });
    }
}
