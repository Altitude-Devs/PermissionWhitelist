package com.alttd.config;

public final class Messages extends AbstractConfig {

    static Messages messages;
    static int version;

    public Messages() {
        super("messages.yml");
    }

    public static void reload() {
        messages = new Messages();

        version = messages.getInt("config-version", 1);
        messages.set("config-version", 1);

        messages.readConfig(Messages.class, null);
    }

    public static String NO_PERMISSION = "<red>You do not have permission to do that.</red>";
    public static String NO_CONSOLE = "<red>You cannot use this command from console.</red>";

    private static void loadGeneric() {
        NO_PERMISSION = messages.getString("generic.no-permission", NO_PERMISSION);
        NO_CONSOLE = messages.getString("generic.no-console", NO_CONSOLE);
    }

    public static String NOT_WHITELISTED = "<red>You are not whitelisted on <server></red>";
    public static String ALREADY_OFF = "<gold>Whitelist was already <red>off</red>, nothing changed!</gold>";
    public static String ALREADY_ON = "<gold>Whitelist was already <green>on</green>, nothing changed!</gold>";
    public static String TURNED_OFF = "<gold>Turned the whitelist <red>off</red> for <server>!</gold>";
    public static String TURNED_ON = "<gold>Turned the whitelist <green>on</green> for <server>!</gold>";
    public static String INVALID_SERVER = "<red><server> is not a valid server.</red>";
    public static String ENFORCED_WHITELIST = "<green>Enforced whitelist for <server></green>";
    private static void loadWhitelistMessages() {
        NOT_WHITELISTED = messages.getString("messages.not-whitelisted", NOT_WHITELISTED);
        ALREADY_OFF = messages.getString("messages.already-off", ALREADY_OFF);
        ALREADY_ON = messages.getString("messages.already-on", ALREADY_ON);
        TURNED_OFF = messages.getString("messages.turned-off", TURNED_OFF);
        TURNED_ON = messages.getString("messages.turned-on", TURNED_ON);
        INVALID_SERVER = messages.getString("messages.invalid-server", INVALID_SERVER);
        ENFORCED_WHITELIST = messages.getString("messages.enforced-whitelist", ENFORCED_WHITELIST);
    }

    public static String HELP_MESSAGE_WRAPPER = "<gold>PermissionWhitelist help:\n<commands></gold>";
    public static String HELP_COMMAND_OFF = "<gold>/permissionwhitelist off <server></gold>: <green>Turns the whitelist off for specified server</green>";
    public static String HELP_COMMAND_ON = "<gold>/permissionwhitelist on <server></gold>: <green>Turns the whitelist on for specified server</green>";
    public static String HELP_COMMAND_HELP = "<gold>/permissionwhitelist help</gold>: <green>Shows this menu</green>";
    public static String HELP_COMMAND_ENFORCE = "<gold>/permissionwhitelist enforce <server></gold>: <green>Enforces whitelist for specified server</green>";
    private static void loadCommandMessages() {
        HELP_MESSAGE_WRAPPER = messages.getString("help.wrapper", HELP_MESSAGE_WRAPPER);
        HELP_COMMAND_OFF = messages.getString("help.command-off", HELP_COMMAND_OFF);
        HELP_COMMAND_ON = messages.getString("help.command-on", HELP_COMMAND_ON);
        HELP_COMMAND_HELP = messages.getString("help.command-help", HELP_COMMAND_HELP);
        HELP_COMMAND_ENFORCE = messages.getString("help.command-enforce", HELP_COMMAND_ENFORCE);
    }
}
