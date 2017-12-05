package com.nonclercq.caspar.iginterviewtest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nonclercq.caspar.iginterviewtest.exceptions.InvalidOrdersException;
import com.nonclercq.caspar.iginterviewtest.schemas.DestinationConfiguration;
import com.nonclercq.caspar.iginterviewtest.schemas.DestinationType;
import com.nonclercq.caspar.iginterviewtest.services.OrdersService;

@Controller
public class OrderController
{
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	private final OrdersService ordersService;

	public OrderController(final OrdersService ordersService)
	{
		this.ordersService = ordersService;
	}

	@RequestMapping(path = "/1.0/formsubmit", method = RequestMethod.POST)
	public ResponseEntity<?> handleForm(@RequestParam("url") final String url,
	                                    @RequestParam("file") final MultipartFile multipartFile,
	                                    @RequestParam("user") final String user,
	                                    @RequestParam("password") final String password,
	                                    @RequestParam("destination") final String destinationString,
	                                    @RequestParam("type") final String type) throws InvalidOrdersException
	{
		final DestinationConfiguration destinationConfiguration = new DestinationConfiguration(url, user, password, destinationString, DestinationType.from(type));
		ordersService.submitOrders(destinationConfiguration, multipartFile);
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/")
	@ResponseBody
	public String renderForm()
	{
		return "<!DOCTYPE HTML>\n" +
			"<html>\n" +
			"<head>\n" +
			"    <title>Caspar Nonclercq - IG Interview Test </title>\n" +
			"</head>\n" +
			"<body>\n" +
			"<h2>Submitted Files</h2>\n" +
			"<table>\n" +
			"    <form action=\"/1.0/formsubmit\" method=\"POST\" enctype=\"multipart/form-data\">\n" +
			"        <tr>\n" +
			"            <td>ConnectionString:</td>\n" +
			"            <td><input type=\"text\" name=\"url\"/></td>\n" +
			"        </tr>\n" +
			"        <tr>\n" +
			"            <td>Orders XML file:</td>\n" +
			"            <td><input type=\"file\" name=\"file\"/> (First line must be xml decleration and orders must be one per line)</td>\n" +
			"        </tr>\n" +
			"        <tr>\n" +
			"            <td>Username:</td>\n" +
			"            <td><input type=\"text\" name=\"user\"/></td>\n" +
			"        </tr>\n" +
			"        <tr>\n" +
			"            <td>Password:</td>\n" +
			"            <td><input type=\"text\" name=\"password\"/></td>\n" +
			"        </tr>\n" +
			"        <tr>\n" +
			"            <td>Destination name:</td>\n" +
			"            <td><input type=\"text\" name=\"destination\"/></td>\n" +
			"        </tr>\n" +
			"        <tr>\n" +
			"            <td>Type:</td>\n" +
			"            <td>\n" +
			"                <!-- TODO Radio button far more sensible - verify OK-->\n" +
			"                <input type=\"radio\" id=\"topic\" name=\"type\" value=\"TOPIC\"> <label for=\"topic\">Topic</label>\n" +
			"                <input type=\"radio\" id=\"queue\" name=\"type\" value=\"QUEUE\"> <label for=\"queue\">Queue</label></td>\n" +
			"        </tr>\n" +
			"        <tr>\n" +
			"            <td></td>\n" +
			"            <td><input type=\"submit\" value=\"submit\"/></td>\n" +
			"        </tr>\n" +
			"    </form>\n" +
			"</table>\n" +
			"</body>\n" +
			"</html>";
	}
}
