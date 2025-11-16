package net.soggart.potionmod.item.custom;

//import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireballGauntlet extends Item {
    public FireballGauntlet(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            Vec3d look = user.getRotationVec(1.0F); // Vetor de direção baseado no olhar do jogador

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

            fireball.setVelocity(look.x, look.y, look.z, 1.5F, 0.0F); // Opcional: velocidade adicional

            world.spawnEntity(fireball);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}