package giselle.rs_upgrade_jade;

import com.refinedmods.refinedstorage.api.network.node.INetworkNode;
import com.refinedmods.refinedstorage.api.network.node.INetworkNodeProxy;

import snownee.jade.api.IServerDataProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class UpgradeServerDataProvider implements IServerDataProvider<BlockEntity>
{
	public static UpgradeServerDataProvider INSTANCE = new UpgradeServerDataProvider();

	@Override
	public ResourceLocation getUid()
	{
		return JadePlugin.TOOLTIP_ID;
	}

	@Override
	public void appendServerData(CompoundTag compound, ServerPlayer player, Level level, BlockEntity blockEntity, boolean showDetails)
	{
		if (blockEntity instanceof INetworkNodeProxy<?>)
		{
			INetworkNode node = ((INetworkNodeProxy<?>) blockEntity).getNode();
			IItemHandler upgrades = NetworkNodeUpgradesHelper.getUpgrades(node);

			if (upgrades == null)
			{
				return;
			}

			int slots = upgrades.getSlots();
			ItemStackHandler merged = new ItemStackHandler(slots);

			for (int slot = 0; slot < slots; slot++)
			{
				ItemStack stack = upgrades.getStackInSlot(slot);

				if (stack.isEmpty())
				{
					continue;
				}

				ItemHandlerHelper.insertItemStacked(merged, stack.copy(), false);
			}

			ListTag data = new ListTag();

			for (int slot = 0; slot < slots; slot++)
			{
				ItemStack stack = merged.getStackInSlot(slot);

				if (stack.isEmpty())
				{
					continue;
				}

				data.add(stack.save(new CompoundTag()));
			}

			if (data.size() > 0)
			{
				compound.put(JadePlugin.TOOLTIP_ID_STRING, data);
			}

		}

	}

	private UpgradeServerDataProvider()
	{

	}

}
