package com.nyTimes.webClientService;

import java.util.LinkedList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nyTimes.model.News;
import com.nyTimes.model.NyList;

import reactor.core.publisher.Mono;

@Service
@RedisHash("nyNews")
public class NyNewsWebService {

	private WebClient webClient = WebClient.create();

	
	/**
	 * 
	 * estrae le top news del nytimes
	 * 
	 * @return lista delle notizie del ny time
	 */
	@Cacheable("nyNews")
	public List<News> search() {

		Mono<NyList> nyList =
				webClient
				.get()
				.uri("https://api.nytimes.com/svc/topstories/v2/science.json?api-key=lrO3y7ujzL09S95JgWFZcBeySIJkGqmq")
				.retrieve()
				.bodyToMono(NyList.class);
		
		List<News> news = new LinkedList<>(nyList.block().getResults());
		return news;

	}

}
