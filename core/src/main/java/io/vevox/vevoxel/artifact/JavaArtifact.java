package io.vevox.vevoxel.artifact;

import com.avaje.ebean.EbeanServer;
import io.vevox.vevoxel.io.DataHandler;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Matthew Struble
 */
public class JavaArtifact implements Artifact, Plugin {

    protected JavaArtifact(){

    }



    @Override
    public File getDataFolder() {
        return new File(DataHandler.getDataDirectory(), getDataName());
    }

    @Override
    public File getResourceFolder() {
        return new File(DataHandler.getResourceDirectory(), getDataName());
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return null;
    }

    @Override
    public FileConfiguration getConfig() {
        return null;
    }

    @Override
    public InputStream getResource(String s) {
        return getClass().getResourceAsStream(s);
    }

    @Override
    public void saveConfig() {

    }

    @Override
    public void saveDefaultConfig() {

    }

    @Override
    public void saveResource(String s, boolean b) {
        File target = new File(getResourceFolder(), s);
        if (target.exists() && b && !target.delete())
            throw new RuntimeException(new IOException("Failed to delete old resource " + s));
        if (!target.exists())
            try {
                FileUtils.copyURLToFile(getClass().getResource(s), target, 5, 5);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public void reloadConfig() {

    }

    @Override
    public PluginLoader getPluginLoader() {
        return null;
    }

    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public boolean isNaggable() {
        return false;
    }

    @Override
    public void setNaggable(boolean b) {

    }

    @Override
    public EbeanServer getDatabase() {
        return null;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String s, String s1) {
        return null;
    }

    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    @Override
    public ArtifactInfo getInfo() {
        return null;
    }
}
