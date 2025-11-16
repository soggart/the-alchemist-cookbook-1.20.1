package net.soggart.potionmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.soggart.potionmod.TheAlchemistCookBook;
import net.soggart.potionmod.block.ModBlocks;
import net.soggart.potionmod.potion.ModPotions;

public class ModItemGroups {

    public static final ItemGroup POTIONMODGROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(TheAlchemistCookBook.MOD_ID, "slime_rubber"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.potionmod"))
            .icon(() -> new ItemStack(ModItems.SLIMERUBBER)).entries((displayContext, entries) -> {

                entries.add(ModBlocks.SILVERORE_BLOCK);
                entries.add(ModItems.RAWSILVER);
                entries.add(ModBlocks.RAWSILVER_BLOCK);
                entries.add(ModItems.SILVER);
                entries.add(ModBlocks.SILVER_BLOCK);
                entries.add(ModBlocks.PYRITEORE_BLOCK);
                entries.add(ModItems.PYRITE);
                entries.add(ModBlocks.PYRITE_BLOCK);
                entries.add(ModItems.FOOLSGOLD);
                entries.add(ModBlocks.FOOLSGOLD_BLOCK);
                entries.add(ModItems.SLIMERUBBER);


                    }).build());

    public static void registerItemGroups(){
        TheAlchemistCookBook.LOGGER.info("registering item groups for: "+TheAlchemistCookBook.MOD_ID);
    }
}
