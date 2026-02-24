package com.tornhost.tornclient.mods.render;

import com.tornhost.tornclient.mods.Mod;

public class FreeLookMod extends Mod {

	private boolean disabledOnHypixel = true;

	public FreeLookMod() {
		super("FreeLook", "Allows free camera look around the player", Category.RENDER);
	}

	public boolean isDisabledOnHypixel() {
		return disabledOnHypixel;
	}

	public void setDisabledOnHypixel(boolean disabledOnHypixel) {
		this.disabledOnHypixel = disabledOnHypixel;
	}
}
		super("FreeLook", "Allows free camera look around the player", Category.RENDER);
	}
}
