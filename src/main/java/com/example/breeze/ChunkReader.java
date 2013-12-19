package com.example.breeze;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Jethro Bakker
 */
public class ChunkReader implements InitializingBean {

	private Resource feed;
	private BlockingQueue<String> queue = new LinkedBlockingQueue<>();

	public String next() throws Exception {
		return queue.take();
	}

	public void setFeed(Resource feed) {
		this.feed = feed;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Scanner scanner = new Scanner(feed.getInputStream());

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			for (int i = 0; i < 1000; i++)
				queue.put(line);
		}

		scanner.close();
	}


}
