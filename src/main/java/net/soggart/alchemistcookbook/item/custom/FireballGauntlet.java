package net.soggart.alchemistcookbook.item.custom;

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
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireballGauntlet extends BowItem {
    public FireballGauntlet(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                float f = getPullProgress(i);
                if (!(f < 0.001)) {
                    if (!world.isClient) {

                        Vec3d look = user.getRotationVec(1.0F); // Vetor de direção baseado no olhar do jogador

                        if(f >= 1.0f){
                            FireballEntity fireball = new FireballEntity(world, user, look.x, look.y, look.z, 2);

                            // Define o "power" da fireball (quanto mais alto, maior a explosão)
                            fireball.powerX = look.x;
                            fireball.powerY = look.y;
                            fireball.powerZ = look.z;

                            fireball.setPos( // Posiciona a fireball na frente do jogador
                                    user.getX() + look.x * 2.0,
                                    user.getBodyY(0.5) + 0.5,
                                    user.getZ() + look.z * 2.0
                            );

                            fireball.setVelocity(look.x, look.y, look.z, 1.0f, 0.0f);
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

                            fireball.setVelocity(look.x, look.y, look.z, 1.0f, (1-(f*100)));
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

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            }
        }
    }
}