package com.alttd.commands.subcommands;

import com.alttd.PermissionWhitelist;
import com.alttd.commands.SubCommand;
import com.alttd.config.Config;
import com.alttd.config.Messages;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommandOff extends SubCommand {
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

        if (Config.WHITELIST_STATES.getOrDefault(serverName,false) == true) {
            source.sendMessage(getMiniMessage().deserialize(Messages.ALREADY_OFF, TagResolver.resolver("server", Tag.preProcessParsed(serverName))));
            return;
        }

        Config.setWhitelist(serverName,false);

        source.sendMessage(getMiniMessage().deserialize(Messages.TURNED_OFF, TagResolver.resolver("server", Tag.preProcessParsed(serverName))));
    }

    @Override
    public String getName() {
        return "off";
    }

    @Override
    public List<String> getTabComplete(CommandSource source, String[] args) {
        if (args.length <= 1)
            return PermissionWhitelist.getInstance().getProxyServer().getAllServers().stream()
                    .map(registeredServer -> registeredServer.getServerInfo().getName())
                    .collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public String getHelpMessage() {
        return Messages.HELP_COMMAND_OFF;
    }
}
