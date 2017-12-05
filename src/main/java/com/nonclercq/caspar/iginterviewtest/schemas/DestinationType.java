package com.nonclercq.caspar.iginterviewtest.schemas;

import java.util.HashMap;
import java.util.Map;

public enum DestinationType
{
	TOPIC,
	QUEUE;

	private static final Map<String, DestinationType> map = new HashMap<>();

	static
	{
		for (final DestinationType destinationType : DestinationType.values())
		{
			map.put(destinationType.toString(), destinationType);
		}
	}

	public static DestinationType from(final String string)
	{
		return map.get(string);
	}
}
