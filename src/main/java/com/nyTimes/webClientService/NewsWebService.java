package com.nyTimes.webClientService;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Service;

import com.nyTimes.configurations.JedisConfig;
import com.nyTimes.model.News;
import com.nyTimes.model.NyList;

import reactor.core.publisher.Mono;

@Service
@RedisHash("news")
public class NewsWebService {

	@Autowired
	private HackerNewsWebService hackerNewsWebService;
	@Autowired
	private NyNewsWebService nyNewsWebService;
	@Autowired
	JedisConfig jedis;

	/**
	 * List<News> getNewsList()
	 * 
	 * @return lista di notizie prese da hacker-news e dal new york time ordinate
	 *         per data
	 */
	@Cacheable("news")
	public List<News> getNewsList() {

		List<News> newsList = new LinkedList<>(
				hackerNewsWebService.searchByItem(hackerNewsWebService.getItemHackNews()));
		List<News> nyList = nyNewsWebService.search();

		newsList.addAll(nyList.parallelStream().map(i -> i).collect(Collectors.toList()));
		
		
		return newsList;
	}

}
