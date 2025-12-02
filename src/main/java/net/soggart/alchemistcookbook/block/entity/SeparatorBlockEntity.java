package net.soggart.alchemistcookbook.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.soggart.alchemistcookbook.block.custom.SeparatorBlock;
import net.soggart.alchemistcookbook.item.ModItems;
import net.soggart.alchemistcookbook.screen.SeparatorScreenHandler;
import org.jetbrains.annotations.Nullable;

public class SeparatorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int OUTPUT_SLOT2 = 2;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxprogress = 64;

    private int capacity1 = 0;
    private int capacity2 = 0;
    private final int maxcapacity1 = 72;
    private final int maxcapacity2 = 72;

    public SeparatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SEPARATOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> SeparatorBlockEntity.this.progress;
                    case 1 -> SeparatorBlockEntity.this.maxprogress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> SeparatorBlockEntity.this.progress = value;
                    case 1 -> SeparatorBlockEntity.this.maxprogress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("separator.displayname");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("separator.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("separator.progress");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SeparatorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()){return;}

        if(isOutputSlotEmptyOrReceivable()){
            if(this.hasRecipe()){
                this.increaseCraftProgress();
                markDirty(world, pos, state);

                if(hasCraftingFinished()){
                    this.craftItem();
                    this.resetProgress();
                }
            }else{
                this.resetProgress();
            }
        }else{
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasCraftingFinished() {
        return progress>=maxprogress;
    }

    private void craftItem() {
        if(this.increaseCapacity(1, 3) && this.increaseCapacity(2, 1)){
            this.removeStack(INPUT_SLOT, 1);
            ItemStack result = new ItemStack(ModItems.EMPTYSYRINGEITEM);

            this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT).getCount() + result.getCount()));
        }
    }

    private boolean increaseCapacity(int tank, int amount) {
        switch (tank){
            case 1 -> {
                return this.maxcapacity1 < capacity1 + amount;
            }
            case 2 -> {
                return this.maxcapacity2 < capacity2 + amount;
            }
            default -> {
                return false;
            }
        }

    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasRecipe() {
        ItemStack result = new ItemStack(ModItems.EMPTYSYRINGEITEM);
        boolean hasInput = getStack(INPUT_SLOT).getItem() == ModItems.FILLEDSYRINGEITEM;

        return hasInput && canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.getItem());
    }


    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }
}
