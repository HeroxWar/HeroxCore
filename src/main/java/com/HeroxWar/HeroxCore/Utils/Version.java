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
     * For the new version 26.x
     * Ex. 26.1 -> 26 -> true
     * Ex. 26.1 -> 25 -> false
     *
     * @param versionPart
     * @return boolean
     */
    public boolean isEquals(int versionPart) {
        boolean match = serverVersion.matches(".*\\b1\\.(" + versionPart + ")\\b.*");
        if(!match) {
            return serverVersion.matches(".*\\b(" + versionPart + ")\\b.*");
        }
        return true;
    }

    /**
     * Check in range
     * Ex. 1.21.8 -> from=18 to=21 -> true
     * Ex. 1.21.8 -> from=18 to=20 -> false
     *
     * For the new version 26.x
     * Ex. 26.1 -> from=25 to=26 -> true
     * Ex. 26.1 -> from=25 to=25 -> false
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
