package io.vevox.vevoxel.artifact;

import java.io.File;

/**
 * @author Matthew Struble
 */
public interface Artifact {

    ArtifactInfo getInfo();

    default ArtifactInfo.ArtifactMeta getMeta(){
        return getInfo().meta;
    }

    default String getDataName(){
        return getInfo().name.toLowerCase().replace(' ','-');
    }

    File getResourceFolder();



}
