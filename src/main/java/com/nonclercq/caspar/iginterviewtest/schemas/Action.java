package com.nonclercq.caspar.iginterviewtest.schemas;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Action
{
	BUY,
	SELL;

	private static final Map<String, Action> map;

	static
	{
		map = new HashMap<>();

		for (final Action action : Action.values())
		{
			map.put(action.toString(), action);
		}
	}

	@JsonCreator
	public static Action get(final String string)
	{
		return map.get(string);
	}
}
