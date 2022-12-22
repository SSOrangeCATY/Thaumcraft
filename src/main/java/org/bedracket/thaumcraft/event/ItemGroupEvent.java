package org.bedracket.thaumcraft.event;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import org.bedracket.thaumcraft.ThaumcraftMod;
import org.bedracket.thaumcraft.init.ModItems;

public class ItemGroupEvent {

    public static void registerCreativeModeTab(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(ThaumcraftMod.MOD_ID, "item_group"),
               builder -> {
                   builder.icon(() -> new ItemStack(ModItems.GOGGLESREVEALING.get()))
                           .displayItems((pEnabledFeatures, pOutput, pDisplayOperatorCreativeTab) -> {
                               pOutput.accept(ModItems.ALUMENTUM.get());
                               pOutput.accept(ModItems.AMBER.get());
                               pOutput.accept(ModItems.BATH_SALTS.get());
                               pOutput.accept(ModItems.BAUBLE_AMULET.get());
                               pOutput.accept(ModItems.BAUBLE_BELT.get());
                               pOutput.accept(ModItems.BAUBLE_RING.get());
                               pOutput.accept(ModItems.BAUBLE_RING_IRON.get());
                               pOutput.accept(ModItems.GOGGLESREVEALING.get());
                           })
                           .title(Component.translatable("itemGroup.thaumcraft.item_group"))
                           .build();
               });
    }
}
