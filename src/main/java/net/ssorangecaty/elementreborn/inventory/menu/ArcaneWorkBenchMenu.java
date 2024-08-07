package net.ssorangecaty.elementreborn.inventory.menu;

import com.google.common.util.concurrent.AtomicDouble;
import net.ssorangecaty.elementreborn.block.entity.ArcaneWorkBenchBlockEntity;
import net.ssorangecaty.elementreborn.inventory.ERInventoryRegister;
import net.ssorangecaty.elementreborn.inventory.slot.OutputWithWandSlot;
import net.ssorangecaty.elementreborn.inventory.slot.WandSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ArcaneWorkBenchMenu extends AbstractContainerMenu {
    public static final int WAND_ROD_SLOT = 10;
    private final Container container;
    private final ContainerLevelAccess access;

    public ArcaneWorkBenchMenu(int p_38963_, Inventory p_38964_
    ) {
        this(ERInventoryRegister.ARCANE_WORKBENCH, p_38963_, p_38964_, new SimpleContainer(11), ContainerLevelAccess.NULL);
    }

    public ArcaneWorkBenchMenu(int syncId, Inventory inventory, Container container, ContainerLevelAccess access
    ) {
        this(ERInventoryRegister.ARCANE_WORKBENCH, syncId, inventory, container, access);
    }


    public ArcaneWorkBenchMenu(
            @Nullable MenuType<?> menuType,
            int syncId,
            Inventory playerInventory,
            Container container,
            ContainerLevelAccess access
    ) {
        super(menuType, syncId);
        this.container = container;
        this.access = access;

        for (int var6 = 0; var6 < 3; var6++) {
            for (int var7 = 0; var7 < 3; var7++) {
                this.addSlot(new Slot(this.container, var7 + var6 * 3, var7 * 24, var6 * 24 - 5));
            }
        }
        this.addSlot(new WandSlot(this.container, WAND_ROD_SLOT, 120, -20));
        this.addSlot(new OutputWithWandSlot(this.container, 9, 120, 20, this));
        for (int var62 = 0; var62 < 3; var62++)
            for (int var72 = 0; var72 < 9; var72++)
                this.addSlot(new Slot(playerInventory, var72 + (var62 * 9) + 9, (var72 * 18) - 24, 106 + (var62 * 18)));
        for (int var63 = 0; var63 < 9; var63++)
            this.addSlot(new Slot(playerInventory, var63, (var63 * 18) - 24, 164));
    }

    public boolean hasEnoughVis(Player player) {
        AtomicBoolean bl = new AtomicBoolean(false);
        this.access.execute((level, blockPos) -> bl.set(((ArcaneWorkBenchBlockEntity) Objects.requireNonNull(level.getBlockEntity(blockPos))).hasEnoughVis(player)));
        return bl.get();
    }

    public float getDefaultVis() {
        AtomicDouble db = new AtomicDouble(0);
        this.access.execute((level, blockPos) -> db.set(((ArcaneWorkBenchBlockEntity) Objects.requireNonNull(level.getBlockEntity(blockPos))).getNeedCost()));
        return db.floatValue();
    }

    public boolean isDefaultCraftingRecipe() {
        AtomicBoolean bl = new AtomicBoolean(true);
        this.access.execute((level, blockPos) -> bl.set(((ArcaneWorkBenchBlockEntity) Objects.requireNonNull(level.getBlockEntity(blockPos))).isDefaultCraftingRecipe()));
        return bl.get();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player p_39391_, int p_39392_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_39392_);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_39392_ == 0) {
                this.access.execute((p_39378_, p_39379_) -> itemstack1.getItem().onCraftedBy(itemstack1, p_39378_, p_39391_));
                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (p_39392_ >= 11 && p_39392_ < 47) {
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (p_39392_ < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_39391_, itemstack1);
            if (p_39392_ == 0) {
                p_39391_.drop(itemstack1, false);
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    @Override
    public void removed(Player p_38940_) {
        super.removed(p_38940_);
        this.container.stopOpen(p_38940_);
        this.access.execute((level, blockPos) -> {
            if (level.isClientSide) return;
            if (level.getBlockEntity(blockPos) instanceof ArcaneWorkBenchBlockEntity entity)
                entity.removeUsers();
        });
    }
}
