package com.alttd.commands;

import com.alttd.commands.subcommands.CommandHelp;
import com.alttd.commands.subcommands.CommandOff;
import com.alttd.commands.subcommands.CommandOn;
import com.alttd.config.Messages;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager implements SimpleCommand {

    private final List<SubCommand> subCommands;

    public CommandManager() {
        subCommands = Arrays.asList(
                new CommandOn(),
                new CommandOff(),
                new CommandHelp(this));
    }

    @Override
    public void execute(SimpleCommand.Invocation invocation) {
        String[] args = invocation.arguments();
        CommandSource source = invocation.source();
        SubCommand subCommand;

        if (args.length == 0) {
            subCommand = getSubCommand("help");
        } else {
            subCommand = getSubCommand(args[0]);
        }

        if (subCommand == null)
            subCommand = getSubCommand("help");

        if (!source.hasPermission(subCommand.getPermission())) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Messages.NO_PERMISSION));
            return;
        }

        subCommand.onCommand(source, args);
    }

    @Override
    public List<String> suggest(SimpleCommand.Invocation invocation) {
        String[] args = invocation.arguments();
        CommandSource source = invocation.source();
        List<String> res = new ArrayList<>();

        if (args.length <= 1) {
            res.addAll(subCommands.stream()
                    .filter(subCommand -> source.hasPermission(subCommand.getPermission()))
                    .map(SubCommand::getName)
                    .filter(name -> args.length == 0 || name.startsWith(args[0]))
                    .collect(Collectors.toList())
            );
        } else {
            SubCommand subCommand = getSubCommand(args[0]);
            if (subCommand != null && source.hasPermission(subCommand.getPermission()))
                res.addAll(subCommand.getTabComplete(source, args).stream()
                        .filter(str -> str.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                        .collect(Collectors.toList()));
        }
        return res;
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    private SubCommand getSubCommand(String cmdName) {
        return subCommands.stream()
                .filter(subCommand -> subCommand.getName().equals(cmdName))
                .findFirst()
                .orElse(null);
    }
}
