package com.nexclient.client.mods.render;

import com.nexclient.client.mods.Mod;

public class TimeChangerMod extends Mod {

	private long customTime = 6000L;

	public TimeChangerMod() {
		super("TimeChanger", "Changes the visual time of day", Category.RENDER);
	}

	public long getCustomTime() {
		return customTime;
	}

	public void setCustomTime(long customTime) {
		this.customTime = customTime;
	}
}
