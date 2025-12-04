package net.soggart.alchemistcookbook.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.soggart.alchemistcookbook.utils.ItemNbtHelper;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Objects;

public class EntityDataHolderItem extends Item{
    public EntityDataHolderItem(Item.Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        String needletooltip1 = ItemNbtHelper.getString(stack, "needletarget", ":(");
        //String needletooltip2 = Objects.requireNonNull(ItemNbtHelper.get(stack, "CustomPotionEffects")).toString();


        tooltip.add(Text.translatable(needletooltip1));
        //tooltip.add(Text.translatable(needletooltip2));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
