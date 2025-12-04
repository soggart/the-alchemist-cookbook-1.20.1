package net.soggart.alchemistcookbook.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.soggart.alchemistcookbook.item.ModItems;
import net.soggart.alchemistcookbook.utils.ItemNbtHelper;
import java.util.ArrayList;
import java.util.List;

public class EmptySyringeItem extends Item {
    public EmptySyringeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);
        ItemStack needle = new ItemStack(ModItems.FILLEDSYRINGEITEM);


        List<StatusEffectInstance> list = new ArrayList<>(entity.getActiveStatusEffects().values());
        NbtCompound cmp = new NbtCompound();
        ItemNbtHelper.setString(needle, "needletarget", entity.getType().getName().getString());
        ItemNbtHelper.setCompound(needle, "CustomPotionEffects", cmp);
        System.out.println(cmp);
        PotionUtil.setCustomPotionEffects(needle, list);

        entity.damage(entity.getDamageSources().generic(), 1.0f);

        return TypedActionResult.success(this.fill(itemStack, user, needle)).getResult();
    }

    protected ItemStack fill(ItemStack stack, PlayerEntity player, ItemStack outputStack) {
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        return ItemUsage.exchangeStack(stack, player, outputStack);
    }
}
