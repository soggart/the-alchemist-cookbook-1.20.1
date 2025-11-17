package net.soggart.alchemistcookbook.item.custom;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireballGauntlet extends BowItem {
    public FireballGauntlet(Settings settings) {
        super(settings);
    }
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            boolean bl = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemStack = playerEntity.getProjectileType(stack);
            if (!itemStack.isEmpty() || bl) {
                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(Items.FIRE_CHARGE);
                }

                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                float f = getPullProgress(i);
                if (!(f < 0.001)) {
                    boolean bl2 = bl && itemStack.isOf(Items.FIRE_CHARGE);
                    if (!world.isClient) {

                        Vec3d look = user.getRotationVec(1.0F); // Vetor de direção baseado no olhar do jogador

                        if(f >= 1.0f){
                            FireballEntity fireball = new FireballEntity(EntityType.FIREBALL, world);


                            fireball.setOwner(user); // Define o dono (importante para não causar dano ao próprio jogador)

                            // Define o "power" da fireball (quanto mais alto, maior a explosão)
                            fireball.powerX = look.x;
                            fireball.powerY = look.y;
                            fireball.powerZ = look.z;

                            fireball.setPos( // Posiciona a fireball na frente do jogador
                                    user.getX() + look.x * 2.0,
                                    user.getBodyY(0.5) + 0.5,
                                    user.getZ() + look.z * 2.0
                            );
                            float vel = 1.0f;
                            float dive = 0.0f;

                            int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                            if (j > 0) {
                                fireball.damage(fireball.getDamageSources().fireball(fireball, user), j*1.5f);
                            }

                            int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                            if (k > 0) {
                                vel += k;
                            }

                            fireball.setVelocity(look.x, look.y, look.z, vel, dive);
                            world.spawnEntity(fireball);
                        }else{
                            SmallFireballEntity fireball = new SmallFireballEntity(EntityType.SMALL_FIREBALL, world);
                            fireball.setOwner(user); // Define o dono (importante para não causar dano ao próprio jogador)

                            // Define o "power" da fireball (quanto mais alto, maior a explosão)
                            fireball.powerX = look.x;
                            fireball.powerY = look.y;
                            fireball.powerZ = look.z;

                            fireball.setPos( // Posiciona a fireball na frente do jogador
                                    user.getX() + look.x * 2.0,
                                    user.getBodyY(0.5) + 0.5,
                                    user.getZ() + look.z * 2.0
                            );
                            float vel = 1.0f;
                            float dive = 0.0f;

                            int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                            if (j > 0) {
                                fireball.damage(fireball.getDamageSources().fireball(fireball, user), j*1.5f);
                            }

                            int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                            if (k > 0) {
                                vel += k;
                            }

                            fireball.setVelocity(look.x, look.y, look.z, vel, dive);
                            world.spawnEntity(fireball);
                        }
                    }

                    world.playSound(
                            null,
                            playerEntity.getX(),
                            playerEntity.getY(),
                            playerEntity.getZ(),
                            SoundEvents.ENTITY_BLAZE_SHOOT,
                            SoundCategory.PLAYERS,
                            1.0F,
                            1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F
                    );
                    if (!bl2 && !playerEntity.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            playerEntity.getInventory().removeOne(itemStack);
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }
}