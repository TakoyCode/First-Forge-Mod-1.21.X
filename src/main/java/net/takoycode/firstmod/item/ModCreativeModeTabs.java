package net.takoycode.firstmod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.takoycode.firstmod.FirstMod;
import net.takoycode.firstmod.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FirstMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FIRSTMOD_ITEM_TAB = CREATIVE_MODE_TABS.register("firstmod_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.DIRTRITE.get()))
                    .title(Component.translatable("creativetab.firstmod.firstmod_items"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.DIRTRITE_NUGGET.get());
                        pOutput.accept(ModItems.DIRTRITE.get());
                    })
                    .build()
    );

    public static final RegistryObject<CreativeModeTab> FIRSTMOD_BLOCKS_TAB = CREATIVE_MODE_TABS.register("firstmod_blocks_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.GRASS_SLAB.get()))
                    .withTabsBefore(FIRSTMOD_ITEM_TAB.getId())
                    .title(Component.translatable("creativetab.firstmod.firstmod_blocks"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.DRAVEL.get());
                        pOutput.accept(ModBlocks.DIRT_SLAB.get());
                        pOutput.accept(ModBlocks.GRASS_SLAB.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
