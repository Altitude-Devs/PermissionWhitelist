package com.alttd.commands.subcommands;

import com.alttd.commands.CommandManager;
import com.alttd.commands.SubCommand;
import com.alttd.config.Messages;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandHelp extends SubCommand {

    private final CommandManager commandManager;

    public CommandHelp(CommandManager commandManager) {
        super();
        this.commandManager = commandManager;
    }

    @Override
    public void onCommand(CommandSource source, String[] args) {
        source.sendMessage(getMiniMessage().deserialize(Messages.HELP_MESSAGE_WRAPPER.replaceAll("<commands>", commandManager
                .getSubCommands().stream()
                .filter(subCommand -> source.hasPermission(subCommand.getPermission()))
                .map(SubCommand::getHelpMessage)
                .collect(Collectors.joining("\n")))));
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public List<String> getTabComplete(CommandSource source, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public String getHelpMessage() {
        return Messages.HELP_COMMAND_HELP;
    }
}
