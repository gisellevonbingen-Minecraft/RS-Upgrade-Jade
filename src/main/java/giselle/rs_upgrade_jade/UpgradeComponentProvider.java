package giselle.rs_upgrade_jade;

import java.util.List;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants.NBT;
import snownee.jade.util.HackyRenderableTextComponent;

public class UpgradeComponentProvider implements IComponentProvider
{
	public static UpgradeComponentProvider INSTANCE = new UpgradeComponentProvider();

	@Override
	public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config)
	{
		CompoundNBT serverData = accessor.getServerData();

		if (serverData.contains(JadePlugin.TOOLTIP_ID_STRING, NBT.TAG_LIST))
		{
			CompoundNBT renderData = new CompoundNBT();
			renderData.put("data", serverData.getList(JadePlugin.TOOLTIP_ID_STRING, NBT.TAG_COMPOUND));
			renderData.putFloat("iconScale", ClientConfig.ICON_SCALE.get().floatValue());
			renderData.putBoolean("textBold", ClientConfig.TEXT_BOLD.get());
			renderData.putInt("textColor", ClientConfig.TEXT_COLOR.get());
			tooltip.add(new HackyRenderableTextComponent(JadePlugin.TOOLTIP_ID, renderData));
		}

	}

	private UpgradeComponentProvider()
	{

	}

}
