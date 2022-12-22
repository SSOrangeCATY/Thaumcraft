package net.archasmiel.thaumcraft.capability;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class WandElementCapabilityProvider {
    CompoundTag nbt = new CompoundTag();
    CompoundTag elements = new CompoundTag();
    String codeName;
    int element = 0;
    int maxElement = 100;
    static int maxFire = 100;
    static int maxWater = 100;
    static int maxOrder = 100;
    static int maxChaos = 100;
    static int maxWind = 100;
    static int maxEarth = 100;
    static int wind;
    static int earth;
    static int fire;
    static int water;
    static int order;
    static int chaos;
    public WandElementCapabilityProvider(CompoundTag nbt ,int element,int maxElement){
        this.element = element;
        this.maxElement = maxElement;
        setCustomMaxElement(maxElement);
        setCustomElement(nbt);
    }
    public WandElementCapabilityProvider(CompoundTag nbt ,int element,int maxFire,int maxWater,int maxEarth,int maxOrder,int maxChaos,int maxWind){
        this.element = element;
        WandElementCapabilityProvider.maxFire = maxFire;
        WandElementCapabilityProvider.maxWater = maxWater;
        WandElementCapabilityProvider.maxEarth = maxEarth;
        WandElementCapabilityProvider.maxOrder = maxOrder;
        WandElementCapabilityProvider.maxChaos = maxChaos;
        WandElementCapabilityProvider.maxWind = maxWind;
        setCustomMaxElement(maxElement);
        setCustomElement(nbt);
    }
    public void setCustomElement(CompoundTag nbt){
        nbt.putInt("fire",element);
        nbt.putInt("max_fire",maxFire);
        nbt.putInt("wind",element);
        nbt.putInt("max_wind",maxWind);
        nbt.putInt("water",element);
        nbt.putInt("max_water",maxWater);
        nbt.putInt("earth",element);
        nbt.putInt("max_earth",maxEarth);
        nbt.putInt("chaos",element);
        nbt.putInt("max_chaos",maxChaos);
        nbt.putInt("order",element);
        nbt.putInt("max_order",maxOrder);
        elements = nbt;
    }
    public void setCustomMaxElement(int maxElement){
        maxChaos = maxElement;
        maxEarth = maxElement;
        maxOrder = maxElement;
        maxFire = maxElement;
        maxWater = maxElement;
        maxWind = maxElement;
    }

}
