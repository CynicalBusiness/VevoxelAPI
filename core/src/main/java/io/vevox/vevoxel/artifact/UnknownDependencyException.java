package io.vevox.vevoxel.artifact;

/**
 * @author Matthew Struble
 */
public class UnknownDependencyException extends Exception {

    public UnknownDependencyException(int index){
        super("Unknown or invalid dependency #" + index);
    }

}
