package net.soggart.alchemistcookbook.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.soggart.alchemistcookbook.block.ModBlocks;

public class WorldEaterBlock extends Block {
    public WorldEaterBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        PlayerEntity user = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 20.0, false);
        if(world.getDifficulty().equals(Difficulty.HARD)) {
            if (world.getBlockState(pos).getBlock().equals(ModBlocks.WORLDEATERBLOCK)) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
            if (world.getBlockState(pos.east().up().south()).getBlock().equals(ModBlocks.WORLDEATERBLOCK)) {
                world.setBlockState(pos.east().up(), Blocks.AIR.getDefaultState());
            }
            if (world.getBlockState(pos.west().up().north()).getBlock().equals(ModBlocks.WORLDEATERBLOCK)) {
                world.setBlockState(pos.west().up(), Blocks.AIR.getDefaultState());
            }
            if (world.getBlockState(pos.south().up().west()).getBlock().equals(ModBlocks.WORLDEATERBLOCK)) {
                world.setBlockState(pos.south().up(), Blocks.AIR.getDefaultState());
            }
            if (world.getBlockState(pos.north().up().east()).getBlock().equals(ModBlocks.WORLDEATERBLOCK)) {
                world.setBlockState(pos.north().up(), Blocks.AIR.getDefaultState());
            }
            if (world.getBlockState(pos.east().down().south()).getBlock().equals(ModBlocks.WORLDEATERBLOCK)) {
                world.setBlockState(pos.east().down(), Blocks.AIR.getDefaultState());
            }
            if (world.getBlockState(pos.west().down().north()).getBlock().equals(ModBlocks.WORLDEATERBLOCK)) {
                world.setBlockState(pos.west().down(), Blocks.AIR.getDefaultState());
            }
            if (world.getBlockState(pos.south().down().west()).getBlock().equals(ModBlocks.WORLDEATERBLOCK)) {
                world.setBlockState(pos.south().down(), Blocks.AIR.getDefaultState());
            }
            if (world.getBlockState(pos.north().down().east()).getBlock().equals(ModBlocks.WORLDEATERBLOCK)) {
                world.setBlockState(pos.north().down(), Blocks.AIR.getDefaultState());
            }
        }
        else if(user != null){
                if(world.getBlockState(pos.up()).isIn(BlockTags.SCULK_REPLACEABLE)){
                    world.setBlockState(pos.up(), ModBlocks.WORLDEATERBLOCK.getDefaultState());
                }
                if(world.getBlockState(pos.down()).isIn(BlockTags.SCULK_REPLACEABLE)){
                    world.setBlockState(pos.down(), ModBlocks.WORLDEATERBLOCK.getDefaultState());
                }
                if(world.getBlockState(pos.east()).isIn(BlockTags.SCULK_REPLACEABLE)){
                    world.setBlockState(pos.east(), ModBlocks.WORLDEATERBLOCK.getDefaultState());
                }
                if(world.getBlockState(pos.west()).isIn(BlockTags.SCULK_REPLACEABLE)){
                    world.setBlockState(pos.west(), ModBlocks.WORLDEATERBLOCK.getDefaultState());
                }
                if(world.getBlockState(pos.south()).isIn(BlockTags.SCULK_REPLACEABLE)){
                    world.setBlockState(pos.south(), ModBlocks.WORLDEATERBLOCK.getDefaultState());
                }
                if(world.getBlockState(pos.north()).isIn(BlockTags.SCULK_REPLACEABLE)){
                    world.setBlockState(pos.north(), ModBlocks.WORLDEATERBLOCK.getDefaultState());
                }
        }if((!world.getBlockState(pos.up()).isIn(BlockTags.SCULK_REPLACEABLE) || world.getBlockState(pos.up()).isAir()) && (!world.getBlockState(pos.down()).isIn(BlockTags.SCULK_REPLACEABLE) || world.getBlockState(pos.down()).isAir()) && (!world.getBlockState(pos.east()).isIn(BlockTags.SCULK_REPLACEABLE) || world.getBlockState(pos.east()).isAir()) && (!world.getBlockState(pos.west()).isIn(BlockTags.SCULK_REPLACEABLE) || world.getBlockState(pos.west()).isAir()) && (!world.getBlockState(pos.north()).isIn(BlockTags.SCULK_REPLACEABLE) || world.getBlockState(pos.north()).isAir()) && (!world.getBlockState(pos.south()).isIn(BlockTags.SCULK_REPLACEABLE) || world.getBlockState(pos.south()).isAir())){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        super.randomTick(state, world, pos, random);
    }
}
