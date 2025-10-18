package com.HeroxWar.HeroxCore.Utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
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
    private static final Version version = new Version();

    /**
     * Set a texture of a skull item
     *
     * @param item
     * @param texture
     * @return
     */
    public static ItemStack setCustomTexture(ItemStack item, String texture) throws TextureException {
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
                throw new TextureException("Failed to set skull owner Game Profile, please check if the texture is correct: '" + texture + "'" + exx.getMessage());
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
                    throw new TextureException(e.getMessage());
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
                    throw new TextureException(e.getMessage());
                }
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Set a player head texture of a skull block
     *
     * @param block
     * @param playerName
     */
    public static void setCustomTexture(Block block, String playerName) {
        if (!(block.getState() instanceof Skull)) {
            // change block type
            block.setType(Material.getMaterial(version.isInRange(8, 12) ? "SKULL" : "PLAYER_WALL_HEAD"));
        }
        GameProfile profile = new GameProfile(UUID.randomUUID(), playerName);

        // get Skull Block
        Skull skull = (Skull) block.getState();
        String profileName = null;
        try {
            profileName = getProfileName(profile);
        } catch (TextureException e) {
            throw new RuntimeException(e);
        }

        if (profileName != null) {
            try {
                skull.setOwner(profileName);
            } catch (Exception ignored) {
                skull.setOwnerProfile(Bukkit.getOfflinePlayer(profileName).getPlayerProfile());
            }
        }

        skull.update();
    }

    private static String getProfileName(GameProfile profile) throws TextureException {
        String profileName;

        try {
            // Try new method first: name()
            Method nameMethod = profile.getClass().getDeclaredMethod("name");
            nameMethod.setAccessible(true);
            profileName = (String) nameMethod.invoke(profile);
        } catch (NoSuchMethodException ignored) {
            // Fallback to old method: getName()
            try {
                Method getNameMethod = profile.getClass().getDeclaredMethod("getName");
                getNameMethod.setAccessible(true);
                profileName = (String) getNameMethod.invoke(profile);
            } catch (Exception ex) {
                throw new TextureException(ex.getMessage());
            }
        } catch (Exception e) {
            throw new TextureException(e.getMessage());
        }
        return profileName;
    }

    /**
     * Return the player texture
     *
     * @param p
     * @return
     */
    public static String getPlayerTexture(Player p) {
        try {
            return getPlayerTexture(p, defaultTexture);
        } catch (TextureException ignore) {
            return defaultTexture;
        }
    }

    /**
     * Return the player texture or a default value
     *
     * @param p
     * @param defaultTexture
     * @return
     */
    public static String getPlayerTexture(Player p, String defaultTexture) throws TextureException {
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
                    throw new TextureException(ex.getMessage());
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
            throw new TextureException(ex.getMessage());
        }

        return encod;
    }

    /**
     * Convert a base64 texture to URL
     *
     * @param texture
     * @return
     */
    public static String convertBase64ToURL(String texture) {
        String jsonString = new String(Base64.getDecoder().decode(texture), StandardCharsets.UTF_8);

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject jstextures = jsonObject.getJSONObject("textures");
        JSONObject skin = jstextures.getJSONObject("SKIN");
        return skin.getString("url");
    }

}
