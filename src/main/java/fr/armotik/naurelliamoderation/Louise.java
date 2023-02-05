package fr.armotik.naurelliamoderation;

public class Louise {

    private static final String NAME = "§7[§aLouise§7] : ";

    private Louise() {
        throw new IllegalStateException("Utility Class");
    }

    /**
     * @return louise NAME
     */

    public static String getName() {
        return NAME;
    }

    /**
     * @return chat filter message
     */
    public static String permissionMissing() {
        return getName() + "§c You don't have the permission !";
    }

    /**
     * @return wrong command message
     */
    public static String wrongCommand() {
        return getName() + "§cWrong command. Please respect the schema.";
    }

    /**
     * @return player not found message
     */
    public static String playerNotFound() {
        return getName() + "§cUnknown player";
    }

    /**
     * @return command error message
     */
    public static String commandError() {
        return  getName() + "§cThere was an error during the execution of the order. It could not be completed";
    }

    /**
     * @return chat filter message
     */
    public static String chatFilter() {
        return getName() + "§cYou cant say that !";
    }
}
