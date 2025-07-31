package net.takoycode.firstmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.takoycode.firstmod.block.ModBlocks;
import net.takoycode.firstmod.item.ModCreativeModeTabs;
import net.takoycode.firstmod.item.ModItems;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FirstMod.MOD_ID)
public class FirstMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "firstmod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public FirstMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModItems.DIRTRITE);
            event.accept(ModItems.DIRTRITE_NUGGET);
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.DIRT_SLAB);
            event.accept(ModBlocks.GRASS_SLAB);
            event.accept(ModBlocks.DRAVEL);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void registerBlockColors(RegisterColorHandlersEvent.Block event){
            event.getBlockColors().register(((pState, pLevel, pPos, pTintIndex) -> {
                if(pTintIndex == 0 ){
                    if (pLevel != null && pPos != null){
                        return BiomeColors.getAverageGrassColor(pLevel, pPos);
                    }
                  return GrassColor.getDefaultColor();
                }
                return -1;
            }), ModBlocks.GRASS_SLAB.get());
        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event){
            event.getItemColors().register(((pStack, pTintIndex) -> {
                if(pTintIndex == 0) {
                    return GrassColor.getDefaultColor();
                }
                return -1;
            }), ModBlocks.GRASS_SLAB.get());
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASS_SLAB.get(), RenderType.cutoutMipped());
            });
        }
    }
}
