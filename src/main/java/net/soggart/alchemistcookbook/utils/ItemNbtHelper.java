package net.soggart.alchemistcookbook.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class ItemNbtHelper {
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_ARRAY = new long[0];

    // SETTERS ///////////////////////////////////////////////////////////////////

    public static void set(ItemStack stack, String tag, NbtElement nbt) {
        stack.getOrCreateNbt().put(tag, nbt);
    }

    public static void setBoolean(ItemStack stack, String tag, boolean b) {
        stack.getOrCreateNbt().putBoolean(tag, b);
    }

    public static void setByte(ItemStack stack, String tag, byte b) {
        stack.getOrCreateNbt().putByte(tag, b);
    }

    public static void setShort(ItemStack stack, String tag, short s) {
        stack.getOrCreateNbt().putShort(tag, s);
    }

    public static void setInt(ItemStack stack, String tag, int i) {
        stack.getOrCreateNbt().putInt(tag, i);
    }

    public static void setIntArray(ItemStack stack, String tag, int[] val) {
        stack.getOrCreateNbt().putIntArray(tag, val);
    }

    public static void setLong(ItemStack stack, String tag, long l) {
        stack.getOrCreateNbt().putLong(tag, l);
    }

    public static void setLongArray(ItemStack stack, String tag, long[] val) {
        stack.getOrCreateNbt().putLongArray(tag, val);
    }

    public static void setFloat(ItemStack stack, String tag, float f) {
        stack.getOrCreateNbt().putFloat(tag, f);
    }

    public static void setDouble(ItemStack stack, String tag, double d) {
        stack.getOrCreateNbt().putDouble(tag, d);
    }

    public static void setCompound(ItemStack stack, String tag, NbtCompound cmp) {
        if (!tag.equalsIgnoreCase("ench")) // not override the enchantments
        {
            stack.getOrCreateNbt().put(tag, cmp);
        }
    }

    public static void setString(ItemStack stack, String tag, String s) {
        stack.getOrCreateNbt().putString(tag, s);
    }

    public static void setList(ItemStack stack, String tag, NbtList list) {
        stack.getOrCreateNbt().put(tag, list);
    }

    public static void removeEntry(ItemStack stack, String tag) {
        stack.removeSubNbt(tag);
    }

    // GETTERS ///////////////////////////////////////////////////////////////////

    public static boolean verifyExistance(ItemStack stack, String tag) {
        return !stack.isEmpty() && stack.hasNbt() && stack.getOrCreateNbt().contains(tag);
    }

    public static boolean verifyType(ItemStack stack, String tag, Class<? extends NbtElement> tagClass) {
        return !stack.isEmpty() && stack.hasNbt() && tagClass.isInstance(stack.getOrCreateNbt().get(tag));
    }

    @Nullable
    public static NbtElement get(ItemStack stack, String tag) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().get(tag) : null;
    }

    public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getBoolean(tag) : defaultExpected;
    }

    public static byte getByte(ItemStack stack, String tag, byte defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getByte(tag) : defaultExpected;
    }

    public static short getShort(ItemStack stack, String tag, short defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getShort(tag) : defaultExpected;
    }

    public static int getInt(ItemStack stack, String tag, int defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getInt(tag) : defaultExpected;
    }

    public static int[] getIntArray(ItemStack stack, String tag) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getIntArray(tag) : EMPTY_INT_ARRAY;
    }

    public static long getLong(ItemStack stack, String tag, long defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getLong(tag) : defaultExpected;
    }

    public static long[] getLongArray(ItemStack stack, String tag) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getLongArray(tag) : EMPTY_LONG_ARRAY;
    }

    public static float getFloat(ItemStack stack, String tag, float defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getFloat(tag) : defaultExpected;
    }

    public static double getDouble(ItemStack stack, String tag, double defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getDouble(tag) : defaultExpected;
    }

    /**
     * If nullifyOnFail is true it'll return null if it doesn't find any
     * compounds, otherwise it'll return a new one.
     **/
    @Nullable
    @Contract("_, _, false -> !null")
    public static NbtCompound getCompound(ItemStack stack, String tag, boolean nullifyOnFail) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getCompound(tag) : nullifyOnFail ? null : new NbtCompound();
    }

    @Nullable
    @Contract("_, _, !null -> !null")
    public static String getString(ItemStack stack, String tag, String defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getString(tag) : defaultExpected;
    }

    @Nullable
    @Contract("_, _, _, false -> !null")
    public static NbtList getList(ItemStack stack, String tag, int objtype, boolean nullifyOnFail) {
        return verifyExistance(stack, tag) ? stack.getOrCreateNbt().getList(tag, objtype) : nullifyOnFail ? null : new NbtList();
    }

}
