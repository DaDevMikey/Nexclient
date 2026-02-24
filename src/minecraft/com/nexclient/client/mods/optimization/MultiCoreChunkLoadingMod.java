package com.nexclient.client.mods.optimization;

import com.nexclient.client.mods.Mod;

public class MultiCoreChunkLoadingMod extends Mod {

	private int threadCount = Runtime.getRuntime().availableProcessors();

	public MultiCoreChunkLoadingMod() {
		super("MultiCoreChunks", "Multi-threaded chunk loading for better performance", Category.OPTIMIZATION);
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
}
