package com.HeroxWar.HeroxCore.Utils;

import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Nms {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Nms.class.getName());
    private String packageName = Bukkit.getServer().getClass().getPackage().getName();

    private String nms;
    private boolean nmsVersion;
    private boolean brigadierIsActive;
    private Class<?> minecraftServerClass;

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
        checkMinecraftServerClass();
    }

    public void checkNmsVersion() {
        try {
            Class.forName("net.minecraft.server.MinecraftServer");
            nmsVersion = false;
        } catch (ClassNotFoundException ignore) {
            nmsVersion = true;
        }
    }

    public void checkBrigadier() {
        try {
            Class.forName("com.mojang.brigadier.CommandDispatcher");
            brigadierIsActive = true;
        } catch (Exception ignored) {
            brigadierIsActive = false;
        }
    }

    public void checkMinecraftServerClass() {
        try {
            minecraftServerClass = Class.forName("net.minecraft.server" + (nmsVersion ? nms : ".") + "MinecraftServer");
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            brigadierIsActive = false;
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
