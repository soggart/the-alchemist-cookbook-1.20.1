package net.soggart.alchemistcookbook;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.soggart.alchemistcookbook.block.ModBlocks;
import net.soggart.alchemistcookbook.item.ModItemGroups;
import net.soggart.alchemistcookbook.item.ModItems;
import net.soggart.alchemistcookbook.potion.ModPotions;
import net.soggart.alchemistcookbook.statuseffect.effects.Corrosion;
import net.soggart.alchemistcookbook.statuseffect.effects.Decay;
import net.soggart.alchemistcookbook.statuseffect.effects.Plague;
//import net.soggart.alchemistcookbook.world.gen.ModWorldGeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheAlchemistCookBook implements ModInitializer {
	public static final String MOD_ID = "alchemistcookbook";

    public static final StatusEffect CORROSION = new Corrosion();
    public static final StatusEffect DECAY = new Decay();
    public static final StatusEffect PLAGUE = new Plague();

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

        Registry.register(Registries.STATUS_EFFECT, new Identifier("alchemistcookbook", "corrosion"), CORROSION);
        Registry.register(Registries.STATUS_EFFECT, new Identifier("alchemistcookbook", "decay"), DECAY);
        Registry.register(Registries.STATUS_EFFECT, new Identifier("alchemistcookbook", "plague"), PLAGUE);

        ModItemGroups.registerItemGroups();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModPotions.registerModPotions();

//        ModWorldGeneration.generateModWorldGen();
        
        LOGGER.info("Hello Fabric world!");
	}
}