package io.vevox.vevoxel.artifact;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Arrays;

/**
 * A storage class containing all of the information on an artifact and a
 * parser for the <code>artifact.json</code> file.
 * @author Matthew Struble
 * @since 1.0.0
 */
public class ArtifactInfo {

    /**
     * A storage class for the meta section of an artifact.
     */
    public class ArtifactMeta {

        /**
         * The artifact's prefix if one is defined, otherwise the artifact's full name.
         */
        public final String prefix,
        /**
         * The artifact's description if one is defined, or a "no-description" string if not.
         */
                description;

        protected ArtifactMeta(ArtifactInfo info, JsonObject meta) throws InvalidArtifactException {
            try {
                prefix = meta.has("prefix") ? meta.get("prefix").getAsString() : info.name;
            } catch (IllegalStateException e){
                throw new InvalidArtifactException("Field 'meta.prefix' must be a string");
            }
            try {
                description = meta.has("description") ? meta.get("description").getAsString() : "No description given";
            } catch (IllegalStateException e){
                throw new InvalidArtifactException("Field 'meta.description' must be a string");
            }
        }
    }

    /**
     * A storage class for artifact dependencies.
     */
    public class ArtifactDependency {

        /**
         * The name of the artifact this artifact depends on.
         */
        public final String name,
        /**
         * The version of the dependency, or <code>latest[/branch]</code> for the latest version.
         */
                version;
        /**
         * A boolean for wether or not the dependency is required by the artifact.
         */
        public final boolean required;

        protected ArtifactDependency(JsonObject dependency) throws NullPointerException, IllegalStateException {
            name = dependency.get("name").getAsString();
            version = dependency.get("version").getAsString();
            required = dependency.get("required").getAsBoolean();
        }
    }

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

    /**
     * The name of this artifact.
     */
    public final String name,
    /**
     * The version number of this artifact.
     */
            version;
    /**
     * The main class of this artifact.
     */
    public final Class<? extends JavaArtifact> main;
    /**
     * The time at which this artifact will load.
     */
    public final LoadTime load;
    /**
     * The dependencies of this artifact.
     */
    public final ArtifactDependency[] dependencies;
    /**
     * This artifact's meta data.
     */
    public final ArtifactMeta meta;

    protected ArtifactInfo(JsonObject file) throws InvalidArtifactException, UnknownDependencyException {
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
            throw new InvalidArtifactException("Field 'load' must be one of " + Arrays.toString(LoadTime.values()));
        } finally {
            this.load = load != null ? load : LoadTime.STARTUP;
        }

        try {
            meta = new ArtifactMeta(this, file.has("meta") ? file.getAsJsonObject("meta") : new JsonObject());
        } catch (IllegalStateException e){
            throw new InvalidArtifactException("Field 'meta' must be a JSON object");
        }

        JsonArray dependencies = null;
        try {
            dependencies = file.has("dependencies") ? file.getAsJsonArray("dependencies") : new JsonArray();
        } catch (IllegalStateException e){
            throw new InvalidArtifactException("Field 'dependencies' must be a JSON array");
        }
        this.dependencies = new ArtifactDependency[dependencies.size()];
        for (int i = 0; i < dependencies.size(); i++){
            try {
                this.dependencies[i] = new ArtifactDependency(dependencies.get(i).getAsJsonObject());
            } catch (NullPointerException | IllegalStateException e){
                throw new UnknownDependencyException(i);
            }
        }
    }

}
