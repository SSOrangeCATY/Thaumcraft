package org.bedracket.thaumcraft.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.bedracket.thaumcraft.ThaumcraftMod;
import org.bedracket.thaumcraft.item.MagicItem;

import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS
            = DeferredRegister.create(ForgeRegistries.ITEMS, ThaumcraftMod.MOD_ID);

    public static final RegistryObject<Item> ALUMENTUM =
            registerCommonMagicItem("alumentum", "item");
    public static final RegistryObject<Item> AMBER =
            registerCommonMagicItem("amber", "item");
    public static final RegistryObject<Item> BATH_SALTS =
            registerCommonMagicItem("bath_salts", "item");

    public static final RegistryObject<Item> BAUBLE_AMULET =
            registerRareMagicItem("bauble_amulet", "item");
    public static final RegistryObject<Item> BAUBLE_BELT =
            registerRareMagicItem("bauble_belt", "item");
    public static final RegistryObject<Item> BAUBLE_RING =
            registerRareMagicItem("bauble_ring", "item");
    public static final RegistryObject<Item> BAUBLE_RING_IRON =
            registerRareMagicItem("bauble_ring_iron", "item");
    public static final RegistryObject<Item> GOGGLESREVEALING =
            registerRareMagicItem("gogglesrevealing", "item");
    private static RegistryObject<Item> registerRareMagicItem(String name, String quality) {
        return register(name, () -> new MagicItem(new Item.Properties().stacksTo(1), quality));
    }
    private static RegistryObject<Item> registerCommonMagicItem(String name, String quality) {
        return register(name, () -> new MagicItem(new Item.Properties(), quality));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> itemSup) {
        return ITEMS.register(name, itemSup);
    }
}
