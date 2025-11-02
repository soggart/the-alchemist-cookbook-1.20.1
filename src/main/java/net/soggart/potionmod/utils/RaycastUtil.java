package net.soggart.potionmod.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class RaycastUtil {
    // Don't worry about screenX and screenY. No scary names.
    // screenX is usually (int) (client.mouse.getX() / client.getWindow().getScaleFactor());
    // screenY is usually (int) (client.mouse.getY() / client.getWindow().getScaleFactor());
    public static HitResult raycastFromCustomCamera(int screenX, int screenY, Vec3d cameraPos, float pitch, float yaw, double fov) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return null;

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        float ndcX = (2.0f * screenX) / width - 1.0f;
        float ndcY = 1.0f - (2.0f * screenY) / height;

        Matrix4f projection = new Matrix4f().perspective(
                (float) Math.toRadians(fov),
                width / (float) height,
                0.05f,
                1000.0f
        );

        Matrix4f view = new Matrix4f()
                .rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0))
                .rotate((float) Math.toRadians(yaw + 180), new Vector3f(0, 1, 0));

        Matrix4f invProjectionView = new Matrix4f();
        projection.mul(view, invProjectionView);
        invProjectionView.invert();

        Vector4f rayStart = new Vector4f(ndcX, ndcY, -1, 1.0f);
        Vector4f rayEnd = new Vector4f(ndcX, ndcY, 1, 1.0f);

        rayStart.mul(invProjectionView);
        rayEnd.mul(invProjectionView);

        rayStart.div(rayStart.w);
        rayEnd.div(rayEnd.w);

        Vector3f rayDir = new Vector3f(
                rayEnd.x - rayStart.x,
                rayEnd.y - rayStart.y,
                rayEnd.z - rayStart.z
        ).normalize();

        Vec3d direction = new Vec3d(rayDir.x, rayDir.y, rayDir.z);

        double reach = 160.0; // Change this however you want. The longer, the more lag it's likely going to cause.
        Vec3d end = cameraPos.add(direction.multiply(reach));

        return client.world.raycast(new RaycastContext(
                cameraPos,
                end,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                client.player
        ));
    }
}