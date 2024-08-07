package net.ssorangecaty.elementreborn.core.wands;

import net.ssorangecaty.elementreborn.core.element.IReductionElementsAble;
import net.ssorangecaty.elementreborn.core.element.MagicElement;
import net.ssorangecaty.elementreborn.core.element.StorageElements;
import net.ssorangecaty.elementreborn.core.item.ERItem;
import net.ssorangecaty.elementreborn.data.ERDataComponentRegister;
import net.ssorangecaty.elementreborn.element.ERMagicElements;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WandRod extends ERItem implements IItemStorageElementsAble , IReductionElementsAble {
    private final int maxCapacity;
    private float receiveSpeed = 1.0f;
    private float reductionTime; // example 1.0f = 100% reduction
    private final StorageElements storage = new StorageElements(new HashMap<>());

    public WandRod(ERProperties tcProperties, int maxCapacity, float reductionTime) {
        super(tcProperties);
        this.maxCapacity = maxCapacity;
        this.reductionTime = reductionTime;
        this.tcProperties.tooltips.add(this.getReductionMessage());
    }

    public static StorageElements getElements(ItemStack stack) {
        StorageElements elements = stack.get(ERDataComponentRegister.STORAGE_ELEMENTS);
        if (elements == null)
            elements = stack.set(ERDataComponentRegister.STORAGE_ELEMENTS, new StorageElements(Map.of()));
        return elements;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @NotNull TooltipContext tooltipContext, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, tooltipContext, components, flag);
        StorageElements elements = getElements(itemStack);
        MutableComponent component = Component.empty();
        boolean first = true;
        for (MagicElement element : ERMagicElements.DEFAULT_ELEMENTS) {
            if (!first) component.append(Component.literal(" | "));
            component.append(Component.literal(String.format("%.2f", elements.getOrDefault(element))).withColor(element.getColor()));
            first = false;
        }
        components.add(component);
    }

    @Override
    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public float getReceiveSpeed() {
        return receiveSpeed;
    }

    public void setReceiveSpeed(float receiveSpeed) {
        this.receiveSpeed = receiveSpeed;
    }

    public void setReductionTime(float reductionTime) {
        this.reductionTime = reductionTime;
    }

    public void receiveElement(StorageElements other) {
        if (other == null) {
            return;
        }

        Iterator<MagicElement> iterator = other.getElements().iterator();

        if (!iterator.hasNext()) {
            return;
        }

        MagicElement element;
        do {
            element = iterator.next();
        } while (iterator.hasNext() && other.getElementValue(element) == 0);

        if (this.getStorage().containsElement(element)) {
            float remainingValue = other.getElementValue(element);
            float amountToReceive = Math.min(remainingValue, this.receiveSpeed);

            this.getStorage().addElement(element, amountToReceive);
            other.removeElement(element, amountToReceive, false);
        }
    }

    @Override
    public float getValueAfterReduction(MagicElement element, int value) {
        return value * this.reductionTime;
    }

    public float getReductionTime() {
        return reductionTime;
    }

    @Override
    public Component getReductionMessage() {
        return Component.translatable("item.elementreborn.reduction.tooltip").withStyle(ChatFormatting.DARK_PURPLE).append(Component.literal(" " + (int)(reductionTime * 100) + "%").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    @Override
    public StorageElements getStorage() {
        return this.storage;
    }
}
