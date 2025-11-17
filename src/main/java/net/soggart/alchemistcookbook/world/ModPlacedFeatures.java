package net.soggart.alchemistcookbook.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.soggart.alchemistcookbook.TheAlchemistCookBook;

import java.util.List;

public class ModPlacedFeatures {
	public static final RegistryKey<PlacedFeature> SILVERORE_PLACED_KEY = registerKey("silver_ore_placed");
//	public static final RegistryKey<PlacedFeature> PYRITEORE_PLACED_KEY = registerKey("pyrite_ore_placed");
	

	public static void boostrap(Registerable<PlacedFeature> context) {
		var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

		register(context, SILVERORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SILVERORE_KEY),
				ModOrePlacement.modifiersWithCount(12,
						HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
/*		register(context, PYRITEORE_PLACED_KEY, configuredFeatureRegistryEntryLookUp.getOrThrow(ModConfiguredFeatures.PYRITEORE_KEY),
				ModOrePlacement.modifiersWithCount(12,
						HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
*/	}
	
	public static RegistryKey<PlacedFeature> registerKey(String name) {
		return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(TheAlchemistCookBook.MOD_ID, name));
	}
	
	private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, 
			RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}
	
}