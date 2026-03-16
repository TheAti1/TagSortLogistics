package com.tagsortlogistics;

import net.fabricmc.api.ClientModInitializer;

public class TagSortLogisticsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		TagSortLogistics.LOGGER.info("TagSort Logistics Client initialized!");
	}
}
