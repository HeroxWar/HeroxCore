package com.HeroxWar.HeroxCore;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.world.Coordinate;
import org.mockbukkit.mockbukkit.world.WorldMock;

public class CustomMaterialTest {

    private static ServerMock serverMock;

    @BeforeEach
    public void setUp() {
        serverMock = MockBukkit.mock();
        WorldMock worldMock = new WorldMock();
        worldMock.setName("WorldA");
        BlockMock blockMock = worldMock.createBlock(new Coordinate(100, 100, 100));
        blockMock.setType(Material.LAVA);
        serverMock.addWorld(worldMock);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void newCustomMaterial() {
        CustomMaterial cm = new CustomMaterial("WATER");
        Assertions.assertEquals(Material.WATER, cm.getMaterial());
    }

    @Test
    public void newCustomMaterialLowercase() {
        CustomMaterial cm = new CustomMaterial("diamond");
        Assertions.assertEquals(Material.DIAMOND, cm.getMaterial());
        Assertions.assertEquals("diamond", cm.getOriginalMaterial());
    }

    @Test
    public void newCustomMaterialWithNull() {
        CustomMaterial cm = new CustomMaterial("WATER", true);
        Assertions.assertEquals(Material.WATER, cm.getMaterial());
    }

    @Test
    public void newCustomMaterialWithNullException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new CustomMaterial("WATE", true));
    }

    @Test
    public void newCustomMaterialWithNullDisabled() {
        Assertions.assertDoesNotThrow(() -> new CustomMaterial("WATE", false));
    }

    @Test
    public void newCustomMaterialWithMaterial() {
        CustomMaterial cm = new CustomMaterial(Material.DIRT);
        Assertions.assertEquals(Material.DIRT, cm.getMaterial());
    }

    @Test
    public void newCustomMaterialWithBlock() {
        CustomMaterial cm = new CustomMaterial(serverMock.getWorld("WorldA").getBlockAt(100, 100, 100));
        Assertions.assertEquals(Material.LAVA, cm.getMaterial());
    }

    @Test
    public void newCustomMaterialWithItemStack() {
        CustomMaterial cm = new CustomMaterial(new ItemStack(Material.DIAMOND_SWORD));
        Assertions.assertEquals(Material.DIAMOND_SWORD, cm.getMaterial());
    }

    @Test
    public void getAndSetMaterial() {
        CustomMaterial cm = new CustomMaterial(Material.STONE);
        cm.setMaterial(Material.OBSIDIAN);
        Assertions.assertEquals(Material.OBSIDIAN, cm.getMaterial());
    }

    @Test
    public void getAndSetOriginalMaterial() {
        CustomMaterial cm = new CustomMaterial(Material.STONE);
        Assertions.assertEquals("STONE", cm.getOriginalMaterial());
        cm.setOriginalMaterial("CUSTOM_ORIGIN");
        Assertions.assertEquals("CUSTOM_ORIGIN", cm.getOriginalMaterial());
    }

    @Test
    public void getAndSetOrigin() {
        CustomMaterial cm = new CustomMaterial(Material.STONE);
        Assertions.assertEquals("Material", cm.getOrigin());
        cm.setOrigin("CustomOrigin");
        Assertions.assertEquals("CustomOrigin", cm.getOrigin());
    }

    @Test
    public void originFieldForString() {
        CustomMaterial cm = new CustomMaterial("STONE");
        Assertions.assertEquals("String", cm.getOrigin());
    }

    @Test
    public void originFieldForBlock() {
        CustomMaterial cm = new CustomMaterial(serverMock.getWorld("WorldA").getBlockAt(100, 100, 100));
        Assertions.assertEquals("Block", cm.getOrigin());
    }

    @Test
    public void originFieldForItemStack() {
        CustomMaterial cm = new CustomMaterial(new ItemStack(Material.GRASS_BLOCK));
        Assertions.assertEquals("ItemStack", cm.getOrigin());
    }

    @Test
    public void isNullWithValidMaterial() {
        CustomMaterial cm = new CustomMaterial("STONE");
        Assertions.assertFalse(cm.isNull());
    }

    @Test
    public void isNullWithInvalidMaterial() {
        CustomMaterial cm = new CustomMaterial("INVALID_MATERIAL_XYZ");
        Assertions.assertTrue(cm.isNull());
    }

    @Test
    public void equalsWithSameValues() {
        CustomMaterial a = new CustomMaterial("STONE");
        CustomMaterial b = new CustomMaterial("STONE");
        Assertions.assertEquals(a, b);
    }

    @Test
    public void equalsWithDifferentMaterial() {
        CustomMaterial a = new CustomMaterial("STONE");
        CustomMaterial b = new CustomMaterial("DIRT");
        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void equalsWithDifferentOriginalMaterial() {
        CustomMaterial a = new CustomMaterial("stone");
        CustomMaterial b = new CustomMaterial("STONE");
        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void equalsWithSameOriginalMaterialDifferentMaterial() {
        CustomMaterial a = new CustomMaterial("STONE");
        CustomMaterial b = new CustomMaterial("STONE");
        b.setMaterial(Material.DIRT);
        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void equalsWithNull() {
        CustomMaterial cm = new CustomMaterial("STONE");
        Assertions.assertNotEquals(cm, null);
    }

    @Test
    public void equalsWithNonCustomMaterial() {
        CustomMaterial cm = new CustomMaterial("STONE");
        Assertions.assertNotEquals(cm, "not a CustomMaterial");
    }

    @Test
    public void cloneProducesEqualInstance() {
        CustomMaterial original = new CustomMaterial("STONE");
        CustomMaterial cloned = original.clone();
        Assertions.assertEquals(original.getMaterial(), cloned.getMaterial());
        Assertions.assertNotSame(original, cloned);
    }

    @Test
    public void testToString() {
        CustomMaterial cm = new CustomMaterial("STONE");
        String result = cm.toString();
        Assertions.assertTrue(result.contains("STONE"));
        Assertions.assertTrue(result.contains("String"));
    }

}
