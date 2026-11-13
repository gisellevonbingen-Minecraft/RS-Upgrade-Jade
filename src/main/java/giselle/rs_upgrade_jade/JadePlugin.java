package giselle.rs_upgrade_jade;

import mcp.mobius.waila.api.IWailaClientRegistration;
import mcp.mobius.waila.api.IWailaCommonRegistration;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

@mcp.mobius.waila.api.WailaPlugin
public class JadePlugin implements IWailaPlugin
{
	public static final ResourceLocation TOOLTIP_ID = RS_UpgradeJade.rl("tooltip");
	public static final String TOOLTIP_ID_STRING = TOOLTIP_ID.toString();

	@Override
	public void register(IWailaCommonRegistration registrar)
	{
		registrar.registerBlockDataProvider(UpgradeServerDataProvider.INSTANCE, BlockEntity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registrar)
	{
		registrar.registerComponentProvider(UpgradeComponentProvider.INSTANCE, TooltipPosition.BODY, Block.class);
	}

}
