package net.soggart.alchemistcookbook.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.soggart.alchemistcookbook.item.ModItems;

public class EmptySyringeItem extends Item {
    public EmptySyringeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        ItemStack needle = new ItemStack(ModItems.FILLEDSYRINGEITEM);

        needle.getOrCreateSubNbt("needle.target");// = entity.getActiveStatusEffects();
        needle.getOrCreateSubNbt("needle.effects");// = entity.getActiveStatusEffects();

        needle.setSubNbt("needle.target", (NbtElement) entity);
        needle.setSubNbt("needle.effects", (NbtCompound) entity.getActiveStatusEffects());

        needle.setCustomName(entity.getName());

        entity.damage(entity.getDamageSources().generic(), 0.5f);
        ItemUsage.exchangeStack(user.getActiveItem(), user, needle);
        return super.useOnEntity(stack, user, entity, hand);
    }
}
