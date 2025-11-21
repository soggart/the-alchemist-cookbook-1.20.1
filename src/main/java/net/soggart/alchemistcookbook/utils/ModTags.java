package net.soggart.alchemistcookbook.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.soggart.alchemistcookbook.TheAlchemistCookBook;

public class ModTags {
    public static class Blocks {
        private static TagKey<Block> createTag (String name){
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(TheAlchemistCookBook.MOD_ID, name));
        }

    }
    public static class Items {
        private static TagKey<Item> createTag (String name){
            return TagKey.of(RegistryKeys.ITEM, new Identifier(TheAlchemistCookBook.MOD_ID, name));
        }
    }
}
