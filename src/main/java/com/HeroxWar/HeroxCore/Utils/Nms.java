package com.HeroxWar.HeroxCore.Utils;

import org.bukkit.Bukkit;

public class Nms {

    private String packageName = Bukkit.getServer().getClass().getPackage().getName();

    private String nms;
    private boolean nmsVersion;
    private boolean brigadierIsActive;
    private Class<?> minecraftServerClass;

    /**
     * This constructor check the nms version
     */
    public Nms() {
        try {
            if (packageName.equals("org.bukkit.craftbukkit")) {
                nms = ".";
            } else {
                String[] parts = packageName.split("\\.");
                nms = "." + parts[3] + ".";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            nms = ".";
        }
        checkNmsVersion();
        checkBrigadier();
        try {
            checkMinecraftServerClass();
        } catch (NmsException ignore) {

        }
    }

    /**
     * Return the Class reflected from a specific path
     * @param path
     * @return
     * @throws ClassNotFoundException
     */
    public Class<?> getNMSClass(String path) throws ClassNotFoundException {
        return Class.forName(path.replace(".__VERSION__.", nms));
    }

    private void checkNmsVersion() {
        try {
            nmsVersion = false;
            Class.forName("net.minecraft.server.MinecraftServer");
        } catch (ClassNotFoundException ignore) {
            nmsVersion = true;
        }
    }

    private void checkBrigadier() {
        try {
            Class.forName("com.mojang.brigadier.CommandDispatcher");
            brigadierIsActive = true;
        } catch (Exception ignored) {
            brigadierIsActive = false;
        }
    }

    private void checkMinecraftServerClass() throws NmsException {
        try {
            minecraftServerClass = Class.forName("net.minecraft.server" + (nmsVersion ? nms : ".") + "MinecraftServer");
        } catch (Exception ex) {
            brigadierIsActive = false;
            throw new NmsException(ex.getMessage());
        }
    }

    public String getNms() {
        return nms;
    }

    public void setNms(String nms) {
        this.nms = nms;
    }

    public boolean isNmsVersion() {
        return nmsVersion;
    }

    public void setNmsVersion(boolean nmsVersion) {
        this.nmsVersion = nmsVersion;
    }

    public boolean isBrigadierIsActive() {
        return brigadierIsActive;
    }

    public void setBrigadierIsActive(boolean brigadierIsActive) {
        this.brigadierIsActive = brigadierIsActive;
    }

    public Class<?> getMinecraftServerClass() {
        return minecraftServerClass;
    }

    public void setMinecraftServerClass(Class<?> minecraftServerClass) {
        this.minecraftServerClass = minecraftServerClass;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "Nms{" +
                "packageName='" + packageName + '\'' +
                ", nms='" + nms + '\'' +
                ", nmsVersion=" + nmsVersion +
                ", brigadierIsActive=" + brigadierIsActive +
                ", minecraftServerClass=" + minecraftServerClass +
                '}';
    }
}
