package net.soggart.alchemistcookbook.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;
import net.soggart.alchemistcookbook.world.ModPlacedFeatures;

public class ModOreGeneration {
	public static void generateOres() {
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), 
				GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.SILVERORE_PLACED_KEY);

		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), 
				GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.PYRITEORE_PLACED_KEY);
	}
}
