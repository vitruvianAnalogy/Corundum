package org.corundummc.hub;

import org.corundummc.utils.versioning.Version;

public interface Server {
    /* This interface exists so that I can store a CorundumServer inside a CorundumServerThread without knowing what a CorundumServer by making CorundumServer implement this
     * (since CorundumServer is part of Corundum and the Corundum launcher has to be independent of Corundum) */
    public Version getMCVersion();

    public String getName();

    public Version getVersion();

    public void start(String[] arguments);

    public void quit();
}