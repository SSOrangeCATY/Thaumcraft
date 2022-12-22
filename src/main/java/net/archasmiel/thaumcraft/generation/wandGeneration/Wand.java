package net.archasmiel.thaumcraft.generation.wandGeneration;

import net.archasmiel.thaumcraft.capability.WandElementCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wand extends BowItem {
    private String quality;
    CompoundTag elements = new CompoundTag();
    WandElementCapability handler;
    public Wand(Properties pProperties) {
        super(pProperties);
    }
    public Wand(Properties pProperties, String quality) {
        super(pProperties);
        this.quality =quality;
    }
    public Wand(Properties pProperties,String quality,int element,int maxElement) {
        super(pProperties);
        this.quality = quality;
        handler = new WandElementCapability(elements, element,maxElement);
    }
    public Wand(Properties Properties,String quality,int element,int maxFire,int maxWater,int maxEarth,int maxOrder,int maxChaos,int maxWind) {
        super(Properties);
        this.quality = quality;
        handler = new WandElementCapability(elements,element,maxFire,maxWater,maxEarth,maxOrder,maxChaos,maxWind);
    }
    public String ItemQuality() {return quality;}
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack p_40678_) {
        return UseAnim.BOW;
    }


    public void appendHoverComponent(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(""));
        pTooltipComponents.add(Component.translatable("item.thaumcraft.quality." + ItemQuality()).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("0").withStyle(ChatFormatting.YELLOW)
                .append(Component.translatable(" | ").withStyle(ChatFormatting.WHITE))
                .append(Component.translatable("0").withStyle(ChatFormatting.DARK_GREEN)
                        .append(Component.translatable(" | ").withStyle(ChatFormatting.WHITE))
                        .append(Component.translatable("0").withStyle(ChatFormatting.RED))
                        .append(Component.translatable(" | ").withStyle(ChatFormatting.WHITE))
                        .append(Component.translatable("0").withStyle(ChatFormatting.DARK_AQUA))
                        .append(Component.translatable(" | ").withStyle(ChatFormatting.WHITE))
                        .append(Component.translatable("0").withStyle(ChatFormatting.GRAY))
                        .append(Component.translatable(" | ").withStyle(ChatFormatting.WHITE))
                        .append(Component.translatable("0").withStyle(ChatFormatting.DARK_GRAY))));
        pTooltipComponents.add(Component.translatable("item.thaumcraft." + pStack + ".tips"));
        if (pStack.getTag() == null) {pStack.setTag(handler.getElementsNbt());}
        pTooltipComponents.set(3, Component.translatable(""+ pStack.getTag().getInt("wind")).withStyle(ChatFormatting.YELLOW)
                    .append(Component.translatable(" | ").withStyle(ChatFormatting.WHITE))
                    .append(Component.translatable(""+ pStack.getTag().getInt("earth")).withStyle(ChatFormatting.DARK_GREEN)
                    .append(Component.translatable(" | ").withStyle(ChatFormatting.WHITE))
                    .append(Component.translatable(""+ pStack.getTag().getInt("fire")).withStyle(ChatFormatting.RED))
                    .append(Component.translatable(" | ").withStyle(ChatFormatting.WHITE))
                    .append(Component.translatable(""+ pStack.getTag().getInt("water")).withStyle(ChatFormatting.DARK_AQUA))
                    .append(Component.translatable(" | ").withStyle(ChatFormatting.WHITE))
                    .append(Component.translatable(""+ pStack.getTag().getInt("order")).withStyle(ChatFormatting.GRAY))
                    .append(Component.translatable(" | ").withStyle(ChatFormatting.WHITE))
                    .append(Component.translatable(""+ pStack.getTag().getInt("chaos")).withStyle(ChatFormatting.DARK_GRAY))));
    }

}
