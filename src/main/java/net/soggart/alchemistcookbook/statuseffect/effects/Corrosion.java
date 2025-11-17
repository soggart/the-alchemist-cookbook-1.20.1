package net.soggart.alchemistcookbook.statuseffect.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class Corrosion extends StatusEffect {
    public Corrosion() {
        super(
                StatusEffectCategory.HARMFUL, // whether beneficial or harmful for entities
                0x94ae28); // color in RGB
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
        if (entity instanceof PlayerEntity) {
            if (counter >= 48) {
                entity.getEquippedStack(EquipmentSlot.MAINHAND).damage(1, entity, Entity::detach);
                entity.getEquippedStack(EquipmentSlot.OFFHAND).damage(1, entity, Entity::detach);
                entity.damageArmor(entity.getDamageSources().magic(), 1.4f);
                counter = 0;
            }else{counter+=(amplifier+1);}
        }
    }
}