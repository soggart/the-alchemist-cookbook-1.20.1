package net.soggart.alchemistcookbook.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.soggart.alchemistcookbook.block.entity.SeparatorBlockEntity;

public class SeparatorScreenHandler extends ScreenHandler {
    public final Inventory inventory;
    public final PropertyDelegate propertyDelegate;
    public final SeparatorBlockEntity blockEntity;

    public SeparatorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(5));
    }

    public SeparatorScreenHandler(int syncId, PlayerInventory pinventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {

        super(ModScreenHandler.SEPARATOR_SCREEN_HANDLER, syncId);
        checkSize((Inventory)blockEntity, 3);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(pinventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((SeparatorBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory, 0, 80, 15));
        this.addSlot(new Slot(inventory, 1, 52, 71));
        this.addSlot(new Slot(inventory, 2, 108, 71));

        addPlayerInventory(pinventory);
        addPlayerHotbar(pinventory);
        addProperties(arrayPropertyDelegate);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);  // Max Progress
        int progressArrowSize = 33; // This is the width in pixels of your arrow
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getTankCapacity1() {
        int capacity = this.propertyDelegate.get(2);
        int maxCapacity = this.propertyDelegate.get(4);  // Max Progress
        return maxCapacity != 0 && capacity != 0 ? capacity : 0;
    }

    public int getTankCapacity2() {
        int capacity = this.propertyDelegate.get(3);
        int maxCapacity = this.propertyDelegate.get(4);  // Max Progress
        return maxCapacity != 0 && capacity != 0 ? capacity : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 93 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 151));
        }
    }
}
