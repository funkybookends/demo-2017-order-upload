package com.nonclercq.caspar.iginterviewtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.stream.Collectors;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

public class FileUtils
{
	public static String stringFrom(final String fileName)
	{
		final Resource resource = new DefaultResourceLoader().getResource(fileName);

		try (final InputStream inputStream = resource.getInputStream();
		     final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		     final BufferedReader reader = new BufferedReader(inputStreamReader))
		{
			return reader.lines().collect(Collectors.joining());
		}
		catch (final IOException exception)
		{
			throw new UncheckedIOException(exception);
		}
	}
}
