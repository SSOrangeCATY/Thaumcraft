package org.bedracket.thaumcraft;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.bedracket.thaumcraft.event.ItemGroupEvent;
import org.bedracket.thaumcraft.init.ModItems;

@Mod(ThaumcraftMod.MOD_ID)
public class ThaumcraftMod {

    public static final String MOD_ID = "thaumcraft";

    public ThaumcraftMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modEventBus);
        modEventBus.addListener(ItemGroupEvent::registerCreativeModeTab);
    }
}
