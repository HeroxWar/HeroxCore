package com.HeroxWar.HeroxCore.Utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.UUID;

public class Texture {

    private static final String defaultTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Im51bGwifX19";
    private static final UUID RANDOM_UUID = UUID.fromString("8667ba71-b85a-4004-af54-457a9734eed7");

    public static ItemStack setCustomTexture(ItemStack item, String texture) {
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        try {
            UUID uuid = Bukkit.getOnlinePlayers().stream().findFirst().map(Player::getUniqueId).orElse(RANDOM_UUID);
            PlayerProfile player_profile = Bukkit.getServer().createPlayerProfile(uuid, "Tombs");
            PlayerTextures textures = player_profile.getTextures();
            textures.setSkin(new URL(convertBase64ToURL(texture)));
            player_profile.setTextures(textures);
            try {
                meta.setOwnerProfile(player_profile);
            } catch (NullPointerException exx) {
                System.out.println("Failed to set skull owner Game Profile, please check if the texture is correct: '"+ texture +"'");
                exx.printStackTrace();
            }
        } catch (NoSuchMethodError | MalformedURLException ignored) {
            try {
                GameProfile profile = new GameProfile(UUID.randomUUID(), "");
                profile.properties().put("textures", new Property("textures", texture));
                Field profileField;
                try {
                    profileField = meta.getClass().getDeclaredField("profile");
                    profileField.setAccessible(true);
                    profileField.set(meta, profile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodError ignored2) {
                GameProfile profile = new GameProfile(UUID.randomUUID(), "");
                try {
                    java.lang.reflect.Method getPropertiesMethod = profile.getClass().getDeclaredMethod("getProperties");
                    getPropertiesMethod.setAccessible(true);
                    Object properties = getPropertiesMethod.invoke(profile);
                    java.lang.reflect.Method putMethod = properties.getClass().getDeclaredMethod("put", Object.class, Object.class);
                    putMethod.invoke(properties, "textures", new Property("textures", texture));

                    Field profileField = meta.getClass().getDeclaredField("profile");
                    profileField.setAccessible(true);
                    profileField.set(meta, profile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    public static String getPlayerTexture(Player p) {
        return getPlayerTexture(p, defaultTexture);
    }

    public static String getPlayerTexture(Player p, String defaultTexture) {
        // get GameProfile of the player
        String encod = defaultTexture;

        try {
            // Ottieni la classe CraftPlayer usando la riflessione
            Nms nms = new Nms();
            Class<?> CraftPlayer = nms.getNMSClass("org.bukkit.craftbukkit.__VERSION__.entity.CraftPlayer");
            // Cast entity to CraftPlayer
            Object craftEntity = CraftPlayer.cast(p);
            // Get getProfile Method
            Method getProfile = CraftPlayer.getDeclaredMethod("getProfile");
            // Get GameProfile
            GameProfile profile = (GameProfile) getProfile.invoke(craftEntity, new Object[]{});
            //Collection<Property> c = profile.getProperties().get("textures");
            Collection<Property> c = null;
            try {
                c = (Collection<Property>) profile.properties().get("textures");
            } catch (NoSuchMethodError | Exception ex3) {
                // Fallback to old method: getProperties()
                try {
                    Method getPropertiesMethod = profile.getClass().getDeclaredMethod("getProperties");
                    getPropertiesMethod.setAccessible(true);
                    Object propertyMap = getPropertiesMethod.invoke(profile);
                    c = ((PropertyMap) propertyMap).get("textures");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            // get textures of the player game profile
            for (Property p1 : c) {
                try {
                    Method m = p1.getClass().getDeclaredMethod("getName");
                    if (((String) m.invoke(p1, new Object[]{})).equalsIgnoreCase("textures")) {
                        Method m1 = p1.getClass().getDeclaredMethod("getValue");
                        encod = (String) m1.invoke(p1, new Object[]{});
                        break;
                    }
                } catch (NoSuchMethodException ex) {
                    if (p1.name().equalsIgnoreCase("textures")) {
                        encod = p1.value();
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            // method isn't found or more!
            ex.printStackTrace();
        }

        return encod;
    }

    public static String convertBase64ToURL(String texture) {
        String jsonString = new String(Base64.getDecoder().decode(texture), StandardCharsets.UTF_8);

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject jstextures = jsonObject.getJSONObject("textures");
        JSONObject skin = jstextures.getJSONObject("SKIN");
        return skin.getString("url");
    }

}
