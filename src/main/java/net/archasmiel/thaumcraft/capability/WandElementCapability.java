package net.archasmiel.thaumcraft.capability;

import net.archasmiel.thaumcraft.api.IWandElementHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WandElementCapability extends WandElementCapabilityProvider implements IWandElementHandler {
    public WandElementCapability(CompoundTag nbt ,int element,int maxElement) {
        super(nbt,element,maxElement);
    }
    public WandElementCapability(CompoundTag nbt ,int element,int maxFire,int maxWater,int maxEarth,int maxOrder,int maxChaos,int maxWind) {
        super(nbt,element,maxFire,maxWater,maxEarth,maxOrder,maxChaos,maxWind);
    }

    @Override
    public int getElementByName(CompoundTag nbt, String nameCode) {
        return switch (nameCode) {
            case "wind" -> nbt.getInt("wind");
            case "earth" -> nbt.getInt("earth");
            case "fire" -> nbt.getInt("fire");
            case "water" -> nbt.getInt("water");
            case "order" -> nbt.getInt("order");
            case "chaos" -> nbt.getInt("chaos");
            default -> 0;
        };
    }

    @Override
    public int getMaxElementByName(String nameCode) {
        return switch (nameCode) {
            case "wind" -> maxWind;
            case "earth" -> maxEarth;
            case "fire" -> maxFire;
            case "water" -> maxWater;
            case "order" -> maxOrder;
            case "chaos" -> maxChaos;
            default -> 100;
        };
    }

    @Override
    public CompoundTag getElementsNbt() {
        return elements;
    }
    public boolean addWindElement(ItemStack stack,int number) {if(stack.getTag().getInt("wind")+ number<=maxWind){wind = stack.getTag().getInt("wind") + number;elements = stack.getTag().copy();elements.putInt("wind",wind);stack.setTag(elements);return true;}return false;}
    public boolean addEarthElement(ItemStack stack,int number) {if(stack.getTag().getInt("earth")+ number<=maxEarth){earth = stack.getTag().getInt("earth") + number;elements = stack.getTag().copy();elements.putInt("earth",earth);stack.setTag(elements);return true;}return false;}
    public boolean addFireElement(ItemStack stack,int number) {if(stack.getTag().getInt("fire")+ number<=maxFire){fire = stack.getTag().getInt("fire") + number;elements = stack.getTag().copy();elements.putInt("fire",fire);stack.setTag(elements);return true;}return false;}
    public boolean addWaterElement(ItemStack stack,int number) {if(stack.getTag().getInt("water")+ number<=maxWater){water = stack.getTag().getInt("water") + number;elements = stack.getTag().copy();elements.putInt("water",water);stack.setTag(elements);return true;}return false;}
    public boolean addOrderElement(ItemStack stack,int number) {if(stack.getTag().getInt("order")+ number<=maxOrder){order = stack.getTag().getInt("order") + number;elements = stack.getTag().copy();elements.putInt("order",order);stack.setTag(elements);return true;}return false;}
    public boolean addChaosElement(ItemStack stack,int number) {if(stack.getTag().getInt("chaos")+ number<=maxChaos){chaos = stack.getTag().getInt("chaos") + number;elements = stack.getTag().copy();elements.putInt("chaos",chaos);stack.setTag(elements);return true;}return false;}

}
