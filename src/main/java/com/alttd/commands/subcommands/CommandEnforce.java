package com.alttd.commands.subcommands;

import com.alttd.PermissionWhitelist;
import com.alttd.commands.SubCommand;
import com.alttd.config.Messages;
import com.alttd.util.Util;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandEnforce extends SubCommand {
    @Override
    public void onCommand(CommandSource source, String[] args) {
        if (args.length != 1) {
            source.sendMessage(getMiniMessage().deserialize(getHelpMessage()));
            return;
        }

        String serverName = args[0];
        Optional<RegisteredServer> optionalRegisteredServer = PermissionWhitelist.getInstance().getProxyServer().getServer(serverName);

        if (optionalRegisteredServer.isEmpty()) {
            source.sendMessage(getMiniMessage().deserialize(Messages.INVALID_SERVER, TagResolver.resolver("server", Tag.preProcessParsed(serverName))));
            return;
        }

        RegisteredServer server = optionalRegisteredServer.get();
        Component kickMessage = getMiniMessage().deserialize(Messages.NOT_WHITELISTED, TagResolver.resolver("server", Tag.preProcessParsed(serverName)));

        Util.enforceWhitelistForServer(serverName, server, kickMessage);

        source.sendMessage(getMiniMessage().deserialize(Messages.ENFORCED_WHITELIST, TagResolver.resolver("server", Tag.preProcessParsed(serverName))));
    }

    @Override
    public String getName() {
        return "on";
    }

    @Override
    public List<String> getTabComplete(CommandSource source, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public String getHelpMessage() {
        return Messages.HELP_COMMAND_ENFORCE;
    }
}
