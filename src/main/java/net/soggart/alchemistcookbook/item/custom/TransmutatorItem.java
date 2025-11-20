package net.soggart.alchemistcookbook.item.custom;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class TransmutatorItem extends Item {

    public TransmutatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Random rand = new java.util.Random(context.getWorld().getMoonPhase()*context.getWorld().getTime());



        BlockPos target = context.getBlockPos();
        //PlayerEntity user = context.getPlayer();
        BlockState b = context.getWorld().getBlockState(target);
        //Block bloco = b.getBlock();
        Block newblock = Registries.BLOCK.get(rand.nextInt());

        transmute(b, newblock, rand, context);
        return super.useOnBlock(context);
    }

    public void transmute(BlockState a, Block b, Random rando, ItemUsageContext context){
        if (a != null) {
            Stream<TagKey<Block>> tags = a.streamTags();
            if(tags != null){
                Object tag = tags.toArray()[rando.nextInt()%(int)(tags.count())];
                TagKey<Block> no = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "wither_immune"));
                if(b.getDefaultState().streamTags().anyMatch((Predicate<? super TagKey<Block>>) tag) && !tag.equals(no) && !b.getDefaultState().isAir()){
                    //if(a == b.getDefaultState()){context.getWorld().setBlockState(context.getBlockPos(), Blocks.GOLD_BLOCK.getDefaultState());}
                    context.getWorld().setBlockState(context.getBlockPos(), b.getDefaultState());
                }
                else {
                    transmute(a, Registries.BLOCK.get(rando.nextInt()), rando, context);
                }
            }
        }
    }
}
