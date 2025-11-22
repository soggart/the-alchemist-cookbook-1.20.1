package net.soggart.alchemistcookbook.item.custom;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import java.util.Random;


public class TransmutatorItem extends Item {

    public TransmutatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos target = context.getBlockPos();
        BlockState b = context.getWorld().getBlockState(target);
        transmute(b, context);
        return super.useOnBlock(context);
    }

    public void transmute(BlockState a, ItemUsageContext context){
        if (a != null) {
            int n1 = a.streamTags().toArray().length;
            Random rando = new java.util.Random(context.getWorld().getMoonPhase()*context.getWorld().getTime()*n1* context.toString().length()+context.getWorld().getTickOrder()/573);
            if(a.streamTags().findAny().isPresent()){

                if(!a.isIn(BlockTags.WITHER_IMMUNE) && !a.isAir()){

                    int n = rando.nextInt(n1);
                    TagKey<Block> tag = a.streamTags().toList().get(n);
                    int n2 = Registries.BLOCK.getEntryList(tag).stream().toArray().length;
                    int counter = 0;
                    int temp = n2;
                    int panic = 20;
                    Block b = Registries.BLOCK.getEntryList(tag).stream().toList().get(rando.nextInt(n2)).get(0).value();
                    if(n1 == 1 && n2 == 1){
                        System.out.println(a);
                        System.out.println(b);
                        System.out.println(tag);
                        return;
                    }
                    while(n2<=1 && counter<n1 || a.getBlock() == b || panic<=0){
                        counter++;
                        tag = a.streamTags().toList().get(n);
                        n2 = Registries.BLOCK.getEntryList(tag).stream().toArray().length;
                        if(temp<=n2 || (tag == BlockTags.PICKAXE_MINEABLE || tag == BlockTags.HOE_MINEABLE || tag == BlockTags.SHOVEL_MINEABLE || tag == BlockTags.AXE_MINEABLE)){
                            n--;
                            if(n<=0){n=n1-1;}
                            counter++;
                            b = Registries.BLOCK.getEntryList(tag).stream().toList().get(rando.nextInt(n2)).get(0).value();
                        }panic--;
                    }

                    if(!b.getDefaultState().isIn(BlockTags.WITHER_IMMUNE) && !b.getDefaultState().isAir()){
                        if(b.getDefaultState().isIn(tag)){
                            //if(a == b.getDefaultState()){b = Blocks.BEDROCK;}
                            context.getWorld().setBlockState(context.getBlockPos(), b.getDefaultState());
                            System.out.println(n);
                            System.out.println(n2);
                        }
                    }//else {transmute(a, Registries.BLOCK.get(rando.nextInt()), rando, context);}
                }
            }
        }
    }
}
