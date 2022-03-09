package com.alttd.events;

import com.alttd.config.Config;
import com.alttd.config.Messages;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.Optional;

public class LoginEvent {

    @Subscribe
    public void onPlayerJoin(ServerPreConnectEvent event) {
        Optional<RegisteredServer> optionalRegisteredServer = event.getResult().getServer();
        if (optionalRegisteredServer.isEmpty())
            return;

        RegisteredServer registeredServer = optionalRegisteredServer.get();
        String serverName = registeredServer.getServerInfo().getName();
        if (Config.WHITELIST_STATES.get(serverName) == false)
            return;

        Player player = event.getPlayer();
        if (!player.hasPermission("permissionwhitelist.join.all") && !player.hasPermission("permissionwhitelist.join." + serverName))
        {
            player.createConnectionRequest(event.getOriginalServer());
            player.sendMessage(MiniMessage.miniMessage().deserialize(Messages.NOT_WHITELISTED, TagResolver.resolver("server", Tag.preProcessParsed(serverName))));
        }
    }
}

