package net.soggart.potionmod.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;


public class PestItem extends Item {
    public PestItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        stack.setCount(stack.getCount()+1);
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }
}

