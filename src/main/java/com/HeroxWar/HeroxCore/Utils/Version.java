package com.HeroxWar.HeroxCore.Utils;

import org.bukkit.Bukkit;

public class Version {

    private String serverVersion;
    private String formattedServerVersion;
    private String[] splitFormattedServerVersion;

    public Version() {
        serverVersion = Bukkit.getServer().getVersion();
        decodeServerVersion();
    }

    private void decodeServerVersion() {
        // ...(MC: 1 21 8)
        String[] split = serverVersion.split("MC: ");
        formattedServerVersion = split[split.length - 1];
        formattedServerVersion = formattedServerVersion.substring(0, formattedServerVersion.length() - 1);
        splitFormattedServerVersion = formattedServerVersion.split("\\.");
    }

    /**
     * Check if the version part is equals.
     * Ex. 1.21.8 -> 21 -> true
     * Ex. 1.21.8 -> 20 -> false
     * <p>
     * For the new version 26.x.y
     * Ex. 26.1 -> 26 -> true
     * Ex. 26.1 -> 25 -> false
     *
     * @param versionPart
     * @return boolean
     */
    public boolean isEquals(int versionPart) {
        if(splitFormattedServerVersion[0].equalsIgnoreCase("1")) {
            return serverVersion.matches(".*\\b1\\.(" + versionPart + ")\\b.*");
        } else {
            return serverVersion.matches(".*\\b(" + versionPart + ")\\b.*");
        }
    }

    /**
     * Check in range
     * Ex. 1.21.8 -> from=18 to=21 -> true
     * Ex. 1.21.8 -> from=18 to=20 -> false
     * <p>
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
        decodeServerVersion();
    }

    public String getFormattedServerVersion() {
        return formattedServerVersion;
    }

    public void setFormattedServerVersion(String formattedServerVersion) {
        this.formattedServerVersion = formattedServerVersion;
    }

    public String[] getSplitFormattedServerVersion() {
        return splitFormattedServerVersion;
    }

    public void setSplitFormattedServerVersion(String[] splitFormattedServerVersion) {
        this.splitFormattedServerVersion = splitFormattedServerVersion;
    }
}
