package giselle.rs_upgrade_jade;

import com.refinedmods.refinedstorage.api.network.node.INetworkNode;
import com.refinedmods.refinedstorage.api.network.node.INetworkNodeProxy;

import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class UpgradeServerDataProvider implements IServerDataProvider<TileEntity>
{
	public static UpgradeServerDataProvider INSTANCE = new UpgradeServerDataProvider();

	@Override
	public void appendServerData(CompoundNBT compound, ServerPlayerEntity player, World level, TileEntity blockEntity)
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

			ListNBT data = new ListNBT();

			for (int slot = 0; slot < slots; slot++)
			{
				ItemStack stack = merged.getStackInSlot(slot);

				if (stack.isEmpty())
				{
					continue;
				}

				data.add(stack.serializeNBT());
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
