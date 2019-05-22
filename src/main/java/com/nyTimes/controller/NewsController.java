package com.nyTimes.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nyTimes.model.News;
import com.nyTimes.webClientService.HackerNewsWebService;
import com.nyTimes.webClientService.NewsWebService;
import com.nyTimes.webClientService.NyNewsWebService;

@RestController
public class NewsController {

	@Autowired
	private HackerNewsWebService hackerNewsWebService;
	@Autowired
	private NyNewsWebService nyNewsWebService;
	@Autowired
	private NewsWebService newsWebService;

	// in base a alla {source} otterremmo le liste di nytime o hcknews ordinate
	@RequestMapping(value = "/news/{source}", method = RequestMethod.GET)
	public List<News> getSpecificNews(@PathVariable String source) {
		if (source.equals("ny")) {
			return nyNewsWebService.search();
		} else if (source.equals("hck")) {
			return hackerNewsWebService.searchByItem(hackerNewsWebService.getItemHackNews());
		}
		return null;
	}

	// ritorna una stringa contentenente le news combinate tra nytimes e hcknews
	// ordinate per data
	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public List<News> getListaAggregata() {
		
		return  newsWebService.getNewsList().parallelStream().sorted().collect(Collectors.toList());

	}

}
