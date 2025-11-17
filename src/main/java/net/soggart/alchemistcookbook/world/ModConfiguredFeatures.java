package net.soggart.alchemistcookbook.world;

import net.soggart.alchemistcookbook.TheAlchemistCookBook;
import net.soggart.alchemistcookbook.block.ModBlocks;

import java.util.List;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ModConfiguredFeatures {
	public static final RegistryKey<ConfiguredFeature<?, ?>> SILVERORE_KEY = registerKey("silver_ore");
//	public static final RegistryKey<ConfiguredFeature<?, ?>> PYRITEORE_KEY = registerKey("pyrite_ore");

	public static void boostrap(Registerable<ConfiguredFeature<?, ?>> context) {
		RuleTest stoneReplacables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
		
		List<OreFeatureConfig.Target> overworldSilverOre = 
				List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.SILVERORE_BLOCK.getDefaultState()));

/*		List<OreFeatureConfig.Target> overworldPyriteOre = 
				List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.PYRITEORE_BLOCK.getDefaultState()));
*/
		register(context, SILVERORE_KEY, Feature.ORE, new OreFeatureConfig(overworldSilverOre, 12));
//		register(context, PYRITEORE_KEY, Feature.ORE, new OreFeatureConfig(overworldPyriteOre, 12));
	}
	
	public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(TheAlchemistCookBook.MOD_ID, name));
	}
	
	private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
			RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
	
	
	
}