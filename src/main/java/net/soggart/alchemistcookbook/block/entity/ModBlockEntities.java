package net.soggart.alchemistcookbook.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.soggart.alchemistcookbook.TheAlchemistCookBook;
import net.soggart.alchemistcookbook.block.ModBlocks;

public class ModBlockEntities {
    public static final BlockEntityType<SeparatorBlockEntity> SEPARATOR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(TheAlchemistCookBook.MOD_ID, "separator_block_entity"),
                    FabricBlockEntityTypeBuilder.create(SeparatorBlockEntity::new,
                            ModBlocks.SEPARATOR_BLOCK).build());

    public static void registerBlockEntities() {
        TheAlchemistCookBook.LOGGER.info("Registeries Block Entities for "+TheAlchemistCookBook.MOD_ID);
    }
}
