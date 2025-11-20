package net.soggart.alchemistcookbook.item.custom;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.ToIntFunction;


public class TransmutatorItem extends Item {

    public TransmutatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos target = context.getBlockPos();
        //PlayerEntity user = context.getPlayer();
        BlockState b = context.getWorld().getBlockState(target);
        Block bloco = b.getBlock();
        bloco.getDefaultState().hashCode();
        ToIntFunction<String> ordem = FabricTagProvider.BlockTagProvider.BlockTagProvider.JSON_KEY_SORT_ORDER;
        return super.useOnBlock(context);
    }
}
