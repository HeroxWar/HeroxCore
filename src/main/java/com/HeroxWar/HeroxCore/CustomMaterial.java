package com.HeroxWar.HeroxCore;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class CustomMaterial {

    private String originalMaterial;
    private Material material;
    private String origin;

    /**
     * Create a CustomMaterial object
     *
     * @param material
     * @param checkNullable
     * @throws IllegalArgumentException if the material is null and checkNullable is true
     */
    public CustomMaterial(String material, boolean checkNullable) {
        this(material);
        if (checkNullable) {
            if (this.material == null) {
                throw new IllegalArgumentException("Material cannot be null, this material is not correct " + originalMaterial);
            }
        }
    }

    public CustomMaterial(String material) {
        this.originalMaterial = material;
        this.material = Material.getMaterial(material.toUpperCase());
        this.origin = "String";
    }

    public CustomMaterial(Material material) {
        this.material = material;
        this.originalMaterial = this.material.toString();
        this.origin = "Material";
    }

    public CustomMaterial(Block block) {
        this.material = block.getType();
        this.originalMaterial = this.material.toString();
        this.origin = "Block";
    }

    public CustomMaterial(ItemStack itemStack) {
        this.material = itemStack.getType();
        this.originalMaterial = this.material.toString();
        this.origin = "ItemStack";
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Check if the material is null
     *
     * @return true if the material is null, false otherwise
     */
    public boolean isNull() {
        return this.material == null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CustomMaterial that)) return false;
        return Objects.equals(originalMaterial, that.originalMaterial) && material == that.material && Objects.equals(origin, that.origin);
    }

    @Override
    public CustomMaterial clone() {
        return new CustomMaterial(this.material);
    }

    @Override
    public String toString() {
        return "CustomMaterial{" +
                "originalMaterial='" + originalMaterial + '\'' +
                ", material=" + material +
                ", origin='" + origin + '\'' +
                '}';
    }
}
