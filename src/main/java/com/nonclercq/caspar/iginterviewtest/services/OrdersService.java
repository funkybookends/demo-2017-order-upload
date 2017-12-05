package com.nonclercq.caspar.iginterviewtest.services;

import org.springframework.web.multipart.MultipartFile;

import com.nonclercq.caspar.iginterviewtest.exceptions.InvalidOrdersException;
import com.nonclercq.caspar.iginterviewtest.schemas.DestinationConfiguration;

public interface OrdersService
{
	void submitOrders(DestinationConfiguration destinationConfiguration, MultipartFile multipartFile) throws InvalidOrdersException;
}
