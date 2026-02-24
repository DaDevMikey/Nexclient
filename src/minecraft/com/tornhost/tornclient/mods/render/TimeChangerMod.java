package com.tornhost.tornclient.mods.render;

import com.tornhost.tornclient.mods.Mod;

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
