package net.soggart.alchemistcookbook.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.soggart.alchemistcookbook.TheAlchemistCookBook;
import net.soggart.alchemistcookbook.item.ModItems;

import static net.minecraft.entity.effect.StatusEffects.*;

public class ModPotions {
    public static final Potion GLOWING_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("alchemistcookbook", "glow"),
                    new Potion(
                            new StatusEffectInstance(
                                    GLOWING,
                                    3600,
                                    0)));

    public static final Potion LONG_GLOWING_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("alchemistcookbook", "glow_long"),
                    new Potion(
                            new StatusEffectInstance(
                                    GLOWING,
                                    9600,
                                    0)));

    public static final Potion CORROSION_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("alchemistcookbook", "corrosion"),
                    new Potion(
                            new StatusEffectInstance(
                                    TheAlchemistCookBook.CORROSION,
                                    3600,
                                    0)));

    public static final Potion LONG_CORROSION_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("alchemistcookbook", "corrosion_long"),
                    new Potion(
                            new StatusEffectInstance(
                                    TheAlchemistCookBook.CORROSION,
                                    7200,
                                    0)));

    public static final Potion STRONG_CORROSION_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("alchemistcookbook", "corrosion_strong"),
                    new Potion(
                            new StatusEffectInstance(
                                    TheAlchemistCookBook.CORROSION,
                                    2400,
                                    1)));

    public static final Potion PLAGUE_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("alchemistcookbook", "plague"),
                    new Potion(
                            new StatusEffectInstance(
                                    TheAlchemistCookBook.PLAGUE,
                                    3600,
                                    0)));

    public static final Potion HEALTH_BOOST_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("alchemistcookbook", "health_boost"),
                    new Potion(
                            new StatusEffectInstance(
                                    HEALTH_BOOST,
                                    3600,
                                    0)));

    public static final Potion DECAY_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("alchemistcookbook", "decay"),
                    new Potion(
                            new StatusEffectInstance(
                                    TheAlchemistCookBook.DECAY,
                                    3600,
                                    0)));


    public static void registerModPotions() {
        BrewingRecipeRegistry.registerPotionRecipe(Potions.THICK, Items.GLOW_INK_SAC, GLOWING_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(Potions.THICK, Items.GLOW_BERRIES, GLOWING_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(GLOWING_POTION, Items.REDSTONE, LONG_GLOWING_POTION);

        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, ModItems.PYRITE, CORROSION_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(CORROSION_POTION, Items.REDSTONE, LONG_CORROSION_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(CORROSION_POTION, Items.GLOWSTONE, STRONG_CORROSION_POTION);

        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Items.ZOMBIE_HEAD, PLAGUE_POTION);

        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Items.GOLDEN_APPLE, HEALTH_BOOST_POTION);

        BrewingRecipeRegistry.registerPotionRecipe(HEALTH_BOOST_POTION, Items.FERMENTED_SPIDER_EYE, DECAY_POTION);

        // Use the mixin invoker if you are not using Fabric API
        // BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.WATER, Items.POTATO, TATER_POTION);
    }
}
