package com.nonclercq.caspar.iginterviewtest.schemas;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.xml.annotate.JacksonXmlProperty;
import com.fasterxml.jackson.xml.annotate.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Order")
public class Order implements Serializable
{
	@JacksonXmlProperty(localName = "accont") // TODO what a typo??
	private String account;

	@JacksonXmlProperty(localName = "SubmittedAt")
	private Date submittedAt;

	@JacksonXmlProperty(localName = "ReceivedAt")
	private Date receivedAt;

	@JacksonXmlProperty(localName = "market")
	private String market;

	@JacksonXmlProperty(localName = "action")
	private Action action; // TODO make enum

	@JacksonXmlProperty(localName = "size")
	private long size;

	@JsonCreator
	public Order(@JsonProperty("accont") final String account,
	             @JsonProperty("SubmittedAt") final Date submittedAt,
	             @JsonProperty("ReceivedAt") final Date receivedAt,
	             @JsonProperty("market") final String market,
	             @JsonProperty("action") final Action action,
	             @JsonProperty("size") final long size)
	{
		this.account = account;
		this.submittedAt = submittedAt;
		this.receivedAt = receivedAt;
		this.market = market;
		this.action = action;
		this.size = size;
	}

	public String getAccount()
	{
		return account;
	}

	public Date getSubmittedAt()
	{
		return submittedAt;
	}

	public Date getReceivedAt()
	{
		return receivedAt;
	}

	public String getMarket()
	{
		return market;
	}

	public Action getAction()
	{
		return action;
	}

	public long getSize()
	{
		return size;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final Order order = (Order) o;

		if (size != order.size) return false;
		if (account != null ? !account.equals(order.account) : order.account != null) return false;
		if (submittedAt != null ? !submittedAt.equals(order.submittedAt) : order.submittedAt != null) return false;
		if (receivedAt != null ? !receivedAt.equals(order.receivedAt) : order.receivedAt != null) return false;
		if (market != null ? !market.equals(order.market) : order.market != null) return false;
		return action != null ? action.equals(order.action) : order.action == null;
	}

	@Override
	public int hashCode()
	{
		int result = account != null ? account.hashCode() : 0;
		result = 31 * result + (submittedAt != null ? submittedAt.hashCode() : 0);
		result = 31 * result + (receivedAt != null ? receivedAt.hashCode() : 0);
		result = 31 * result + (market != null ? market.hashCode() : 0);
		result = 31 * result + (action != null ? action.hashCode() : 0);
		result = 31 * result + (int) (size ^ (size >>> 32));
		return result;
	}

	@Override
	public String toString()
	{
		return "Order{" +
			"account='" + account + '\'' +
			", submittedAt=" + submittedAt +
			", receivedAt=" + receivedAt +
			", market='" + market + '\'' +
			", action='" + action + '\'' +
			", size=" + size +
			'}';
	}
}
