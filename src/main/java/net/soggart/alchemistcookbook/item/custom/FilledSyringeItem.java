package net.soggart.alchemistcookbook.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.soggart.alchemistcookbook.utils.ItemNbtHelper;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class FilledSyringeItem extends Item{
    public FilledSyringeItem(Item.Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        String needletooltip1 = ItemNbtHelper.getString(stack, "needletarget", ":(");
        //String needletooltip2 = ItemNbtHelper.getString(stack, "needlefx", "");


        tooltip.add(Text.translatable(needletooltip1));
        //tooltip.add(Text.translatable(needletooltip2));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
