package net.soggart.alchemistcookbook.item.custom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.soggart.alchemistcookbook.utils.RaycastUtil;

public class EnderStaffItem extends Item {
    public EnderStaffItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Ensure we don't spawn the lightning only on the client.
        // This is to prevent desync.
        if (world.isClient) {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
        world.addParticle(ParticleTypes.PORTAL, user.getX(), user.getY(), user.getZ(), 0.0, 0.0, 0.0);

        MinecraftClient client = MinecraftClient.getInstance();
        float fov = MinecraftClient.getInstance().player.getFovMultiplier();
        Vec3d target = RaycastUtil.raycastFromCustomCamera(((int) (client.mouse.getX() / client.getWindow().getScaleFactor())),(int)((client.mouse.getY() / client.getWindow().getScaleFactor())),user.getClientCameraPosVec(1), user.getPitch(), user.getHeadYaw(), fov).getPos();
        //user.getBlockPos().offset(user.getHorizontalFacing(), 10);

        // Spawn the lightning bolt.
        if(target != null){
            user.setPos(target.x, target.y, target.z);
        }


        // Nothing has changed to the item stack,
        // so we just return it how it was.
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
