package giselle.rs_upgrade_jade;

import com.refinedmods.refinedstorage.api.network.node.INetworkNodeProxy;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.util.ResourceLocation;

@mcp.mobius.waila.api.WailaPlugin
public class JadePlugin implements IWailaPlugin
{
	public static final ResourceLocation TOOLTIP_ID = RS_UpgradeJade.rl("tooltip");
	public static final String TOOLTIP_ID_STRING = TOOLTIP_ID.toString();

	@Override
	public void register(IRegistrar registrar)
	{
		registrar.registerBlockDataProvider(UpgradeServerDataProvider.INSTANCE, INetworkNodeProxy.class);
		registrar.registerComponentProvider(UpgradeComponentProvider.INSTANCE, TooltipPosition.BODY, INetworkNodeProxy.class);
		registrar.registerTooltipRenderer(TOOLTIP_ID, UpgradesTooltipRenderer.INSTANCE);
	}

}
