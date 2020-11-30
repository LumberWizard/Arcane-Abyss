package dev.camscorner.arcaneabyss;

import dev.camscorner.arcaneabyss.core.registry.ModBlocks;
import dev.camscorner.arcaneabyss.core.registry.ModItems;
import dev.camscorner.arcaneabyss.core.registry.ModRecipes;
import dev.camscorner.arcaneabyss.core.util.components.IntComponent;
import dev.camscorner.arcaneabyss.core.util.components.SynchedIntComponent;
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class ArcaneAbyss implements ModInitializer, EntityComponentInitializer, ChunkComponentInitializer, ItemComponentInitializer, BlockComponentInitializer
{
	public static final String MOD_ID = "arcaneabyss";
	public static final ComponentKey<IntComponent> ENTROPIC_FLUX = ComponentRegistry.getOrCreate(new Identifier(MOD_ID, "entropic_flux"), IntComponent.class);

	@Override
	public void onInitialize()
	{
		ModItems.register();
		ModBlocks.register();
		ModRecipes.registerSerializers();
		ModRecipes.registerTypes();
		System.out.println("If you gaze long enough into an abyss, the abyss will gaze back into you...");
	}

	@Override
	public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry)
	{
		registry.register(ENTROPIC_FLUX, p -> new SynchedIntComponent());
	}

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
	{
		registry.registerForPlayers(ENTROPIC_FLUX, p -> new SynchedIntComponent(), RespawnCopyStrategy.ALWAYS_COPY);
	}

	@Override
	public void registerItemComponentFactories(ItemComponentFactoryRegistry registry)
	{
		registry.registerFor(i -> true, ENTROPIC_FLUX, p -> new SynchedIntComponent());
	}

	@Override
	public void registerBlockComponentFactories(BlockComponentFactoryRegistry registry)
	{

	}
}
