package com.tornhost.tornclient.mods.chat;

import com.tornhost.tornclient.mods.Mod;

public class NickHiderMod extends Mod {

	private String fakeName = "You";

	public NickHiderMod() {
		super("NickHider", "Hides your player name in chat and tab list", Category.CHAT);
	}

	public String getFakeName() {
		return fakeName;
	}

	public void setFakeName(String fakeName) {
		this.fakeName = fakeName;
	}
}
