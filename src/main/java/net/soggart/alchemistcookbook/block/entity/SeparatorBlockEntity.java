package net.soggart.alchemistcookbook.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
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
import net.soggart.alchemistcookbook.utils.ItemNbtHelper;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class SeparatorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int OUTPUT_SLOT2 = 2;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxprogress = 10;

    public int capacity1 = 0;
    public int capacity2 = 0;
    public int maxcapacity = 72;

    private String bloodsource = "";
    private List<StatusEffectInstance> effects;
    private ItemStack pot = new ItemStack(Items.POTION);

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
        nbt.putInt("separator.tank1", capacity1);
        nbt.putInt("separator.tank2", capacity2);
        if(effects!=null){
            PotionUtil.setCustomPotionEffects(pot, effects);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("separator.progress");
        capacity1 = nbt.getInt("separator.tank1");
        capacity2 = nbt.getInt("separator.tank2");
        effects = PotionUtil.getPotionEffects(pot);
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

        if(isSlotEmptyOrReceivable(OUTPUT_SLOT)){
            if(this.hasRecipea() && (capacity1+3 <= maxcapacity && capacity2+1 <= maxcapacity)){
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

        if(isSlotEmptyOrReceivable(OUTPUT_SLOT)){
            if(this.hasRecipeb() && (capacity1-24 >= 0)){
                markDirty(world, pos, state);
                this.craftItem1();
            }
        }

        if(isSlotEmptyOrReceivable(OUTPUT_SLOT2)){
            if(this.hasRecipec() && (capacity2-24 >= 0)){
                markDirty(world, pos, state);
                this.craftItem2();
            }
        }
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasCraftingFinished() {
        return progress>=maxprogress;
    }

    private void craftItem() {
        //System.out.println(capacity1 + " e " +capacity2);
        effects = PotionUtil.getCustomPotionEffects(this.getStack(0).getNbt());//ItemNbtHelper.get(this.getStack(0), "CustomPotionEffects");
        if(capacity1+3 <= maxcapacity && capacity2+1 <= maxcapacity){
            increaseCapacity1(3);
            if(!effects.isEmpty()){increaseCapacity2(1);}

            bloodsource = ItemNbtHelper.getString(this.getStack(0), "needletarget", "");
            //effects = PotionUtil.getCustomPotionEffects(this.getStack(0).getNbt());//ItemNbtHelper.get(this.getStack(0), "CustomPotionEffects");
            this.removeStack(INPUT_SLOT, 1);
            //ItemStack result = new ItemStack(ModItems.EMPTYSYRINGEITEM);

            //this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT).getCount() + result.getCount()));
        }
    }

    private void craftItem1() {
        if(capacity1-(24*getStack(OUTPUT_SLOT).getCount()) >=0){
            increaseCapacity1(-24*getStack(OUTPUT_SLOT).getCount());
            ItemStack result = new ItemStack(ModItems.BLOODBOTTLE);
            System.out.println(bloodsource);
            ItemNbtHelper.setString(result, "needletarget", bloodsource);
            //this.removeStack(OUTPUT_SLOT);
            this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT).getCount()));
        }
    }

    private void craftItem2() {
        if(capacity2-(24*getStack(OUTPUT_SLOT2).getCount()) >=0){
            increaseCapacity2(-24*getStack(OUTPUT_SLOT2).getCount());
            //this.removeStack(OUTPUT_SLOT2);
            System.out.println(effects);
            if(!effects.isEmpty()){
                //ItemStack result = PotionUtil.setCustomPotionEffects(pot, effects);
                ItemStack result = PotionUtil.setPotion(pot, new Potion());
                this.setStack(OUTPUT_SLOT2, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT2).getCount()));
            }else{System.out.println("porra");}
        }
    }


    private void increaseCapacity1(int amount) {
        if (!(capacity1+amount > maxcapacity || capacity1+amount < 0)){capacity1 += amount;}}

    private void increaseCapacity2(int amount) {
        if (!(capacity2+amount > maxcapacity || capacity2+amount < 0)){capacity2 += amount;}}

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasRecipea() {
        //ItemStack result = new ItemStack(ModItems.EMPTYSYRINGEITEM);
        return getStack(INPUT_SLOT).getItem() == ModItems.FILLEDSYRINGEITEM;//&& canInsertAmountIntoSlot(result, INPUT_SLOT) && canInsertItemIntoSlot(result.getItem(), INPUT_SLOT);
    }

    private boolean hasRecipeb() {
        //ItemStack result = new ItemStack(Items.POTION);
        boolean hasInput1 = getStack(OUTPUT_SLOT).getItem() == Items.GLASS_BOTTLE;

        return hasInput1 && getStack(OUTPUT_SLOT).getCount()<=capacity1/24;
    }
    private boolean hasRecipec() {
        //ItemStack result = new ItemStack(Items.POTION);
        boolean hasInput2 = getStack(OUTPUT_SLOT2).getItem() == Items.GLASS_BOTTLE;

        return hasInput2 && getStack(OUTPUT_SLOT2).getCount()<=capacity2/24;
    }



    private boolean isSlotEmptyOrReceivable(int slot) {
        return this.getStack(slot).isEmpty() || this.getStack(slot).getCount() < this.getStack(slot).getMaxCount();
    }

    private boolean canInsertItemIntoSlot(Item item, int slot) {
        return this.getStack(slot).getItem() == item || this.getStack(slot).isEmpty();
    }

    private boolean canInsertAmountIntoSlot(ItemStack result, int slot) {
        return this.getStack(slot).getCount() + result.getCount() <= getStack(slot).getMaxCount();
    }
}
