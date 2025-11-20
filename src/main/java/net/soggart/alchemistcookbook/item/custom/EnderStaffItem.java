package net.soggart.alchemistcookbook.item.custom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityStatuses;
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
        if (world.isClient) {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
        world.sendEntityStatus(user, EntityStatuses.ADD_PORTAL_PARTICLES);
        MinecraftClient client = MinecraftClient.getInstance();
        float fov = MinecraftClient.getInstance().player.getFovMultiplier();
        Vec3d target = RaycastUtil.raycastFromCustomCamera(((int) (client.mouse.getX() / client.getWindow().getScaleFactor())),(int)((client.mouse.getY() / client.getWindow().getScaleFactor())),user.getClientCameraPosVec(1), user.getPitch(), user.getHeadYaw(), fov, 80.0).getPos();
        if(target != null){
            user.requestTeleport(target.x, target.y, target.z);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
