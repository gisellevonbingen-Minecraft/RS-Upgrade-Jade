package giselle.rs_upgrade_jade;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ClientConfig
{
	public static final ForgeConfigSpec SPEC;

	public static final DoubleValue ICON_SCALE;
	public static final BooleanValue TEXT_BOLD;
	public static final IntValue TEXT_COLOR;

	static
	{
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

		ICON_SCALE = builder.defineInRange("icon_scale", 0.5D, 0.5D, 1.0D);

		TEXT_BOLD = builder.define("text_bold", false);

		builder.comment("RGB Integer");
		TEXT_COLOR = builder.defineInRange("text_color", 0x9E9E9E, 0x000000, 0xFFFFFF);

		SPEC = builder.build();
	}

	private ClientConfig()
	{

	}

}
