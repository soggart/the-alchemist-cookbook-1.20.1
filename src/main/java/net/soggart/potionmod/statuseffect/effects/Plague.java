package net.soggart.potionmod.statuseffect.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.ZombieEntity;
import net.soggart.potionmod.TheAlchemistCookBook;

public class Plague extends StatusEffect {
    public Plague() {
        super(
                StatusEffectCategory.HARMFUL, // whether beneficial or harmful for entities
                0xb096c5); // color in RGB
    }
    int counter = 0;

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
            if (counter >= 400) {
                if(entity.getWorld().getClosestPlayer(entity, 5.0+amplifier) != null) {
                    LivingEntity target = entity.getWorld().getClosestPlayer(entity, 5.4);
                    assert target != null;
                    target.addStatusEffect(new StatusEffectInstance(TheAlchemistCookBook.PLAGUE, counter*(3+amplifier), amplifier + 1, false, true, true));
                }
                if(entity instanceof ZombieEntity){entity.heal(amplifier);}else{entity.damage(entity.getDamageSources().magic(), amplifier + 1);}
                counter = 0;
            }else{counter+=(amplifier+1);}
    }
}