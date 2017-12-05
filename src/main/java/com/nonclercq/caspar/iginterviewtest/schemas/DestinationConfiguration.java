package com.nonclercq.caspar.iginterviewtest.schemas;

public class DestinationConfiguration
{
	private final String url;
	private final String user;
	private final String password;
	private final String destination;
	private final DestinationType type;

	public DestinationConfiguration(final String url,
	                                final String user,
	                                final String password,
	                                final String destination,
	                                final DestinationType type)
	{
		this.url = url;
		this.user = user;
		this.password = password;
		this.destination = destination;
		this.type = type;
	}

	public String getUrl()
	{
		return url;
	}

	public String getUser()
	{
		return user;
	}

	public String getPassword()
	{
		return password;
	}

	public String getDestination()
	{
		return destination;
	}

	public DestinationType getType()
	{
		return type;
	}

	@Override
	public String toString()
	{
		return "DestinationConfiguration{" +
			"url='" + url + '\'' +
			", user='" + user + '\'' +
			", password='***'" + // MASKED PASSWORD
			", destination='" + destination + '\'' +
			", type=" + type +
			'}';
	}
}
