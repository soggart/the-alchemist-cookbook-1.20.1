package net.soggart.potionmod.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.soggart.potionmod.utils.RaycastUtil;

public class LightningStaffItem extends Item {
    public LightningStaffItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Ensure we don't spawn the lightning only on the client.
        // This is to prevent desync.
        if (world.isClient) {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }

        Vec3d target = RaycastUtil.raycastFromCustomCamera((int)(user.getX()),(int)(user.getY()),user.getCameraPosVec(0), user.getPitch(), user.getYaw(), 90).getPos();
        //user.getBlockPos().offset(user.getHorizontalFacing(), 10);

        // Spawn the lightning bolt.
        if(target != null){
            LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightningBolt.setPosition(target);
            world.spawnEntity(lightningBolt);
        }


        // Nothing has changed to the item stack,
        // so we just return it how it was.
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
