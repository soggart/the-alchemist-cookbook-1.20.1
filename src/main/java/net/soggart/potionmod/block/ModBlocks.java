package net.soggart.potionmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.soggart.potionmod.TheAlchemistCookBook;

public class ModBlocks { //quando for fazer um bloco novo lembre-se de preencher blockstates, models, loot tables e lang

    public static final Block SILVERORE_BLOCK = registerBlock("silver_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0F, 6.0F), UniformIntProvider.create(2, 5)));

    public static final Block RAWSILVER_BLOCK = registerBlock("raw_silver_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(3.0F, 6.0F)));

    public static final Block SILVER_BLOCK = registerBlock("silver_block",//copy these two for more blocks
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(3.0F, 6.0F)));

    public static final Block PYRITEORE_BLOCK = registerBlock("pyrite_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0F, 6.0F), UniformIntProvider.create(2, 5)));

    public static final Block PYRITE_BLOCK = registerBlock("raw_fools_gold_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(3.0F, 6.0F)));

    public static final Block FOOLSGOLD_BLOCK = registerBlock("fools_gold_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(3.0F, 6.0F)));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(TheAlchemistCookBook.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(TheAlchemistCookBook.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }


    public static void registerModBlocks(){
        TheAlchemistCookBook.LOGGER.info("registering mod blocks for "+TheAlchemistCookBook.MOD_ID);
    }
}
