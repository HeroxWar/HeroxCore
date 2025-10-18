package com.HeroxWar.HeroxCore.Utils;

import org.bukkit.Bukkit;

public class Version {

    private String serverVersion;

    public Version() {
        serverVersion = Bukkit.getServer().getVersion();
    }

    /**
     * Check if the version part is equals.
     * Ex. 1.21.8 -> 21 -> true
     * Ex. 1.21.8 -> 20 -> false
     *
     * @param versionPart
     * @return boolean
     */
    public boolean isEquals(int versionPart) {
        return serverVersion.matches(".*\\b1\\.(" + versionPart + ")\\b.*");
    }

    /**
     * Check in range
     * Ex. 1.21.8 -> from=18 to=21 -> true
     * Ex. 1.21.8 -> from=18 to=20 -> false
     *
     * @param from
     * @param to
     * @return boolean
     */
    public boolean isInRange(int from, int to) {
        for (int i = from; i <= to; i++) {
            if (isEquals(i)) {
                return true;
            }
        }
        return false;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }
}
