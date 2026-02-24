package com.tornhost.tornclient.mods.chat;

import com.tornhost.tornclient.mods.Mod;

public class AutoGLHFMod extends Mod {

	private String message = "glhf";

	public AutoGLHFMod() {
		super("AutoGLHF", "Automatically sends glhf at the start of a game", Category.CHAT);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
