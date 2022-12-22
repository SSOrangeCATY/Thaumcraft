package net.archasmiel.thaumcraft;

import net.archasmiel.thaumcraft.event.ItemGroupEvent;
import net.archasmiel.thaumcraft.init.ModItemRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ThaumcraftMod.MOD_ID)
public class ThaumcraftMod {

    public static final String MOD_ID = "thaumcraft";

    public ThaumcraftMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItemRegister.ITEMS.register(modEventBus);
        modEventBus.addListener(ItemGroupEvent::registerCreativeModeTab);
    }
}
