package com.clarknoah.neo.service;


import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.clarknoah.neo.repository.TimeRepository;
import com.clarknoah.neo.domain.People;
import com.clarknoah.neo.domain.Time;
import com.clarknoah.neo.relationship.RelTime;
import com.google.gson.*;
import com.clarknoah.neo.repository.PeopleRepository;
public class TimelineJson{
	private PeopleRepository peepRepo;
	//long timeKey = 81;
	//Time mTime;
	//Time time = TimeRepository.findOne(timeKey);
	
	
	public JsonObject mainTimeline =new JsonObject();
	JsonObject headline =new JsonObject();	
	JsonObject asset = new JsonObject();
	JsonObject date = new JsonObject();		
	JsonArray dateArray = new JsonArray();
	Iterable<RelTime> allTime;
	
	//For Headline Object
	String mhl = "Timeline";
	String mty = "default";
	String mtx = "Put text in";
	
	//For Asset Object
	String media = "Web link";
	String thumbnail = "thumbnail link";
	String credit ="source?";
	String caption ="Your caption";

	
	public TimelineJson(Iterable<RelTime> allTime, PeopleRepository pplRepo){	
	//	public TimelineJson(Iterable<RelTime> allTime){	
		this.allTime = allTime;
		this.peepRepo = pplRepo;
		headline.addProperty("headline",mhl);	
		headline.addProperty("type",mty);	
		headline.addProperty("text",mtx);		
		asset.addProperty("media",media);
		asset.addProperty("thumbnail",thumbnail);
		asset.addProperty("credit",credit);
		asset.addProperty("caption",caption);	
	}	
		
	public void addEvent(RelTime element, String title){	
		JsonObject specificEvent = new JsonObject();
         long pplKey = element.getPerson().getNodeId();
        // People intppl = peepRepo.findOne(pplKey);
         People ppl = peepRepo.findOne(pplKey);
        // specificEvent.addProperty(new String("headline")," Name is: "+element.getPerson().getDisplayName());
         specificEvent.addProperty(new String("headline")," Name is: "+ppl.getDisplayName());        		
         specificEvent.addProperty(new String("startDate"), element.getTimeStamp().toString());	 
         specificEvent.addProperty(new String("endDate"), element.getTimeStamp().toString());	 
         specificEvent.addProperty(new String("text"), new String("<p>"+title+"</p><br><p>NodeId:  "+
        		 element.getPerson().getNodeId().toString()+"</p>"));
 		dateArray.add(specificEvent);
		}

	public void lastModifiedJson(){
		String title = "Last Modified";
		Iterator<RelTime> tmCount = allTime.iterator();			
		while(tmCount.hasNext()) {			
			RelTime element = tmCount.next();
			addEvent(element, title);			
	     }
		headline.add("date",dateArray);
		headline.add("asset", asset);		
		mainTimeline.add("timeline", headline);	
		
	}
}


