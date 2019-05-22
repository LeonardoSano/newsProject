package com.nyTimes.webClientService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.common.collect.Lists;
import com.nyTimes.model.HackerNews;
import com.nyTimes.model.News;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RedisHash("hackNews")
public class HackerNewsWebService {

	private WebClient webClient = WebClient.create("https://hacker-news.firebaseio.com");

	/**
	 * getItemHackNews()
	 * 
	 * @return La lista di item necessaria per completare i link per le news di
	 *         HackerNews
	 */
	public Mono<Integer[]> getItemHackNews() {

		return webClient
				.get()
				.uri("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty")
				.retrieve()
				.bodyToMono(Integer[].class);

	}
	
	/**
	 * dati gli itemId di hacker-news
	 * 
	 * @see HackerNewsService
	 * @param alfa
	 * @return tutte le notizie del sito hacker-news
	 */
	@Cacheable("hackNews")
	public List<News> searchByItem(Mono<Integer[]> alfa) {
		List<Integer> alfaList = Arrays.asList(alfa.block());
		List<List<Integer>> alfaSubList = Lists.partition(alfaList, 20);
		List<Mono<HackerNews>> monos = new ArrayList<>();
		
		for (int cont = 0; cont < alfaSubList.size(); cont++) {
			monos.addAll(alfaSubList.get(cont).stream()
					.map(i -> webClient.get().uri("/v0/item/" + i + ".json").retrieve().bodyToMono(HackerNews.class))
					.collect(Collectors.toList()));
		}
		 List<News> news = new LinkedList<>((Flux.mergeSequential(monos)).collectList().block());
		 return news;

	}

}
