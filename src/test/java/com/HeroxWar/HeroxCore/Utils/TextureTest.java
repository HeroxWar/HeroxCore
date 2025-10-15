package com.HeroxWar.HeroxCore.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

public class TextureTest {

    private ServerMock serverMock;

    // Fake Instances
    PlayerMock elio;
    PlayerMock sav;

    @BeforeEach
    public void setUp() {
        // Inizialization server and plugin
        serverMock = MockBukkit.mock();
        elio = serverMock.addPlayer("eliotesta98");
        sav = serverMock.addPlayer("xSavior_of_God");
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server and Plugin
        MockBukkit.unmock();
    }

    @Test
    public void texture() {
        String texture = Texture.getPlayerTexture(elio);
        Assertions.assertEquals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Im51bGwifX19", texture);
        texture = Texture.getPlayerTexture(sav, "a");
        Assertions.assertEquals("a", texture);
    }

    @Test
    public void base64ToUrl() {
        String url = Texture.convertBase64ToURL("ewogICJ0aW1lc3RhbXAiIDogMTcwNTc3NDI4MzE1NSwKICAicHJvZmlsZUlkIiA6ICJlMWM1MTZ" +
                "kYmJiZTI0ZGE3OTEwOTllN2Y0YTUxYTkzOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJlbGlvdGVzdGE5OCIsCiAgInNpZ25hdHVyZVJlc" +
                "XVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmV" +
                "zLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kYWU1Y2UwZDNhNjc3MGUxMWQzNTE2NTM5ZDgzYmRjYTdmZDEyYzVmNzMwMjc4MDhmYTRhM2" +
                "I0MmE5ZjIyODg1IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0sCiAgI" +
                "CAiQ0FQRSIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjM0MGMwZTAzZGQy" +
                "NGExMWIxNWE4YjMzYzJhN2U5ZTMyYWJiMjA1MWIyNDgxZDBiYTdkZWZkNjM1Y2E3YTkzMyIKICAgIH0KICB9Cn0=");
        Assertions.assertEquals("http://textures.minecraft.net/texture/dae5ce0d3a6770e11d3516539d83bdca7fd12c5f73027808fa4a3b42a9f22885", url);
    }

    @Test
    public void setTextureToItem() {
        ItemStack itemStack = new ItemStack(Material.getMaterial("PLAYER_HEAD"), 1);
        Assertions.assertFalse(itemStack.toString().contains("player-profile=CraftPlayerProfile [uniqueId="));
        itemStack = Texture.setCustomTexture(itemStack, "ewogICJ0aW1lc3RhbXAiIDogMTcwNTc3NDI4MzE1NSwKICAicHJvZmlsZUlkIiA6ICJlMWM1MTZ" +
                "kYmJiZTI0ZGE3OTEwOTllN2Y0YTUxYTkzOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJlbGlvdGVzdGE5OCIsCiAgInNpZ25hdHVyZVJlc" +
                "XVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmV" +
                "zLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kYWU1Y2UwZDNhNjc3MGUxMWQzNTE2NTM5ZDgzYmRjYTdmZDEyYzVmNzMwMjc4MDhmYTRhM2" +
                "I0MmE5ZjIyODg1IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0sCiAgI" +
                "CAiQ0FQRSIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjM0MGMwZTAzZGQy" +
                "NGExMWIxNWE4YjMzYzJhN2U5ZTMyYWJiMjA1MWIyNDgxZDBiYTdkZWZkNjM1Y2E3YTkzMyIKICAgIH0KICB9Cn0=");
        Assertions.assertTrue(itemStack.toString().contains("player-profile=CraftPlayerProfile [uniqueId="));
    }

}
