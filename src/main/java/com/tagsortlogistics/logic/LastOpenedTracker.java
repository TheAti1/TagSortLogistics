package com.tagsortlogistics.logic;

import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LastOpenedTracker {
    public static final Map<UUID, BlockPos> lastOpenedPos = new HashMap<>();
}
