package net.archasmiel.thaumcraft.generation;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItemGeneration extends Item {

    private final String quality;

    public ModItemGeneration(Properties pProperties, String quality) {
        super(pProperties);
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("item.thaumcraft.quality."+ getQuality()).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("item.thaumcraft."+ pStack +".tips"));
    }
}
