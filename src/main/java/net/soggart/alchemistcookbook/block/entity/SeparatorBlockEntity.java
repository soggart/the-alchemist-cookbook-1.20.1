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
    private int maxprogress = 128;

    public int capacity1 = 0;
    public int capacity2 = 0;
    public int maxcapacity = 72;

    public SeparatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SEPARATOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> SeparatorBlockEntity.this.progress;
                    case 1 -> SeparatorBlockEntity.this.maxprogress;
                    case 2 -> SeparatorBlockEntity.this.capacity1;
                    case 3 -> SeparatorBlockEntity.this.capacity2;
                    case 4 -> SeparatorBlockEntity.this.maxcapacity;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> SeparatorBlockEntity.this.progress = value;
                    case 1 -> SeparatorBlockEntity.this.maxprogress = value;
                    case 2 -> SeparatorBlockEntity.this.capacity1 = value;
                    case 3 -> SeparatorBlockEntity.this.capacity2 = value;
                    case 4 -> SeparatorBlockEntity.this.maxcapacity = value;
                }
            }

            @Override
            public int size() {
                return 5;
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
            if(this.hasRecipe() && (capacity1+3 <= maxcapacity && capacity2+1 <= maxcapacity)){
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
        increaseCapacity1(3);
        increaseCapacity2(1);
        System.out.println(capacity1 + " e " +capacity2);
        if(capacity1+3 <= maxcapacity && capacity2+1 <= maxcapacity){
            this.removeStack(INPUT_SLOT, 1);
            ItemStack result = new ItemStack(ModItems.EMPTYSYRINGEITEM);
            this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT).getCount() + result.getCount()));
        }
    }

    private void increaseCapacity1(int amount) {
        if (capacity1+amount > maxcapacity){
            return;
        }else{
            capacity1 += amount;
        }
    }
    private void increaseCapacity2(int amount) {
        if (capacity2+amount > maxcapacity){
            return;
        }else{
            capacity2 += amount;
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
