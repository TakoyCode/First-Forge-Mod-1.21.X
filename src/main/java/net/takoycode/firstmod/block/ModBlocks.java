package net.takoycode.firstmod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.takoycode.firstmod.FirstMod;
import net.takoycode.firstmod.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FirstMod.MOD_ID);

    public static final  RegistryObject<Block> DRAVEL = registerBlock("dravel",
            () -> new Block(BlockBehaviour.Properties.of().strength(3f).sound(SoundType.GRAVEL)));

    public static final RegistryObject<SlabBlock> DIRT_SLAB = registerBlock("dirt_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.of()
                    .strength(1f).sound(SoundType.GRAVEL)));

    public static final RegistryObject<SlabBlock> GRASS_SLAB = registerBlock("grass_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.of()
                    .strength(1f).sound(SoundType.GRAVEL)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
