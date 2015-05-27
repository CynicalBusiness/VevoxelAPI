package io.vevox.vevoxel.artifact;

import com.google.gson.JsonObject;

import java.util.Arrays;

/**
 * @author Matthew Struble
 */
public class ArtifactInfo {

    public enum LoadTime {
        /**
         * The artifact can only be enabled when the server is started up, prior to the world.
         */
        STARTUP,
        /**
         * The artifact can only be enabled after the world has finished loading.
         */
        POST_WORLD,
        /**
         * The artifact can be enabled at any time.
         */
        RUNTIME
    }

    public final String name, version;
    public final Class<? extends JavaArtifact> main;
    public final LoadTime load;
    public final ArtifactInfo[] depedencies;

    protected ArtifactInfo(JsonObject file) throws InvalidArtifactException {
        try {
            name = file.get("name").getAsString();
        } catch (NullPointerException | ClassCastException | IllegalStateException e) {
            throw new InvalidArtifactException("Field 'name' must be defined and a string");
        }
        try {
            version = file.get("version").getAsString();
        } catch (NullPointerException | ClassCastException | IllegalStateException e) {
            throw new InvalidArtifactException("Field 'version' must be defined and a string");
        }
        try {
            main = Class.forName(file.get("main").getAsString()).asSubclass(JavaArtifact.class);
        } catch (NullPointerException | ClassCastException | IllegalStateException e) {
            throw new InvalidArtifactException("Field 'version' must be defined and a string");
        } catch (ClassNotFoundException e) {
            throw new InvalidArtifactException("Unknown class '" + file.get("main").getAsString() + "'");
        }
        LoadTime load = null;
        try {
            load = LoadTime.valueOf(file.get("load").getAsString().toUpperCase());
        } catch (NullPointerException e) {
            // no-op
        } catch (ClassCastException | IllegalStateException e) {
            throw new InvalidArtifactException("Field 'load' is not a string");
        } catch (IllegalArgumentException e) {
            throw new InvalidArtifactException("Field 'load' must be " + Arrays.toString(LoadTime.values()));
        } finally {
            this.load = load != null ? load : LoadTime.STARTUP;
        }
    }

}
