package io.vevox.vevoxel.io;

import io.vevox.vevoxel.artifact.Artifact;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

/**
 * A console system for artifact logging.
 * @author Matthew Struble
 * @since 1.0.0
 */
public class ArtifactConsole {

    /**
     * The level of an artifact message.
     */
    public enum MessageLevel {
        /**
         * A standard logging message.
         */
        INFO,
        /**
         * An error message, meaning something could be damaged.
         */
        ERROR,
        /**
         * A warning message, meaning something went wrong but the artifact can continue.
         */
        WARNING,
        /**
         * A message intended to be debug message and will only be displayed in debug mode.
         */
        DEBUG,
        /**
         * A fatal error that the artifact cannot recover from.
         */
        FATAL;

        /**
         * Gets the prefix for this message level.
         * @return The prefix.
         */
        public String getPrefix(){
            switch (this){
                case ERROR:
                    return ChatColor.RED + " ERR:";
                case WARNING:
                    return ChatColor.YELLOW + "WARN:";
                case DEBUG:
                    return ChatColor.LIGHT_PURPLE + " BUG:";
                case FATAL:
                    return ChatColor.DARK_RED + "FAIL:";
                case INFO:
                default:
                    return ChatColor.WHITE + "INFO:";
            }
        }

        /**
         * Determines if this message level is debug-mode only.
         * @return True if this message is a debug message, false otherwise.
         */
        public boolean isDebug(){
            return this == DEBUG;
        }
    }

    private Artifact artifact;
    private ChatColor defaultColor;
    private boolean debugEnabled = false, tagEnabled = true;

    /**
     * Builds a new artifact console from the given artifact and a default color of white.
     * @param artifact The artifact to build the console for.
     */
    public ArtifactConsole(Artifact artifact){
        this(artifact, ChatColor.WHITE);
    }

    /**
     * Builds a new artifact console from the given artifact and default color.
     * @param artifact The artifact to build the console for.
     * @param defaultColor The default color of the console.
     */
    public ArtifactConsole(Artifact artifact, ChatColor defaultColor){
        this.artifact = artifact;
        this.defaultColor = defaultColor;
    }

    /**
     * Gets the default color of this console.
     * @return The default color.
     */
    public ChatColor getDefaultColor(){
        return defaultColor;
    }

    /**
     * Sets the default color of this console to the given color.
     * @param color The new default color.
     */
    public void setDefaultColor(ChatColor color){
        this.defaultColor = color;
    }

    /**
     * Gets the base console command sender.
     * @return The console sender.
     */
    public ConsoleCommandSender getConsole(){
        return Bukkit.getConsoleSender();
    }

    /**
     * Returns whether or not debug mode is enabled on this console.
     * @return True if debug is enabled, false otherwise.
     */
    public boolean isDebugEnabled(){
        return debugEnabled;
    }

    /**
     * Enables or disabled debug mode on this console.
     * @param enabled Whether or not debug is to be enabled.
     */
    public void setDebugEnabled(boolean enabled){
        debugEnabled = enabled;
    }

    /**
     * Returns whether or not the tag is enabled.
     * @return True if the tag is enabled, false otherwise.
     */
    public boolean isTagEnabled(){
        return tagEnabled;
    }

    public void setTagEnabled(boolean enabled){
        tagEnabled = enabled;
    }

    /**
     * Logs a message to the console with the given level.
     * @param level The level of the message to log.
     * @param msg The message to log.
     */
    public void log(MessageLevel level, String msg){
        if (!level.isDebug() || debugEnabled)
            getConsole().sendMessage(level.getPrefix() + getDefaultColor() + " " + msg);
    }

    /**
     * Logs an info message to the console.
     * @param msg The message to log.
     */
    public void info(String msg){
        log(MessageLevel.INFO, msg);
    }

    /**
     * Logs an error message to the console.
     * @param msg The message to log.
     */
    public void error(String msg){
        log(MessageLevel.ERROR, msg);
    }

    /**
     * Logs a warning message to the console.
     * @param msg The message to log.
     */
    public void warning(String msg){
        log(MessageLevel.WARNING, msg);
    }

    /**
     * Logs a debug message to the console.
     * @param msg The message to log.
     */
    public void debug(String msg){
        log(MessageLevel.DEBUG, msg);
    }

    /**
     * Logs a fatal message to the console.
     * @param msg The message to log.
     */
    public void fatal(String msg){
        log(MessageLevel.FATAL, msg);
    }


}
