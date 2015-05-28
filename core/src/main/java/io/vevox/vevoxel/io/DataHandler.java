package io.vevox.vevoxel.io;

import org.bukkit.Bukkit;

import java.io.File;

/**
 * @author Matthew Struble
 */
public class DataHandler {

    public static final String DATA_DIR = ".config", RES_DIR = ".res";

    public static File getDataDirectory(){
        return new File(Bukkit.getWorldContainer(), DATA_DIR);
    }

    public static File getResourceDirectory(){
        return new File(Bukkit.getWorldContainer(), RES_DIR);
    }

}
