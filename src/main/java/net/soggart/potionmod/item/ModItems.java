package net.soggart.potionmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.soggart.potionmod.TheAlchemistCookBook;
import net.soggart.potionmod.item.custom.LightningStaffItem;

public class ModItems {

    public static final Item SLIMERUBBER = registerItem("slime_rubber", new Item(new FabricItemSettings()));
    public static final Item SILVER = registerItem("silver_ingot", new Item(new FabricItemSettings()));
    public static final Item FOOLSGOLD = registerItem("fools_gold", new Item(new FabricItemSettings()));
    public static final Item PYRITE = registerItem("raw_fools_gold", new Item(new FabricItemSettings()));
    public static final Item LIGHTNINGSTAFF = registerItem("lightning_staff", new LightningStaffItem(new FabricItemSettings()));

    private static void addItemstoIngredientItemGroup(FabricItemGroupEntries entries){
        entries.add(SLIMERUBBER);
        entries.add(SILVER);
        entries.add(FOOLSGOLD);
        entries.add(PYRITE);
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(TheAlchemistCookBook.MOD_ID, name), item);
    }

    public static void registerModItems(){
        TheAlchemistCookBook.LOGGER.info("registering mod items for "+TheAlchemistCookBook.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemstoIngredientItemGroup);
    }
}
