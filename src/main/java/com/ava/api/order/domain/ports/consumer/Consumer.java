package com.ava.api.order.domain.ports.consumer;

import java.io.IOException;

public interface Consumer {
	public void consume(String message) throws IOException;
}
