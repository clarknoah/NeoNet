package com.clarknoah.neo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.clarknoah.neo.domain.*;
import com.clarknoah.neo.repository.*;
import com.clarknoah.neo.service.PeopleService;
import com.clarknoah.neo.service.TimeService;


@Controller
public class TestController {

	
	@Autowired 
	private TimeRepository teepRepo;
	@Autowired
	private PeopleRepository peepRepo;
	@Autowired
	private OrganizationRepository orgRepo;
	@Autowired
	private EventRepository eventRepo;
	@Autowired
	private  ProjectRepository projRepo;
	@Autowired
	private PeopleService pplServ;
	
	private long timeKey = 81;
    
//	public final PeopleService ts = new PeopleService(peepRepo,teepRepo);

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
/**
 * 	Methods for creating nodes and committing them to the database
 * 
 * 
 */
	
	@RequestMapping(value = "create/*", method = RequestMethod.GET)
	public String create(Model model) {
		return "create/create";
		}
	@RequestMapping(value = "create/org", method = RequestMethod.GET)
	public String startOrg(Model model) {
		return "create/org";
		}
	
	@RequestMapping(value = "create/addOrg", method = RequestMethod.POST)
	public String addOrg(
            @RequestParam(value = "aName") String orgAcronym,
            @RequestParam(value = "fName") String orgName, 
            Model model) {
			Organization noah = pplServ.createEntity(new Organization(orgName, orgAcronym));
			model = noah.getModel(model);
            
       //  model.addAttribute("displayName", noah.getDisplayName());
       //	 model.addAttribute("nodeType", noah.getNodeType());
       //	 model.addAttribute("nodeId", noah.getNodeId().toString());
		return "result";
		}
	
	@RequestMapping(value = "create/event", method = RequestMethod.GET)
	public String startEvent(Model model) {
		return "create/event";
		}
	
	@RequestMapping(value = "create/addEvent", method = RequestMethod.POST)
	public String addEvent(
			@RequestParam(value = "eventName") String eventName,
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "startTime") String startTime,
            @RequestParam(value = "endDate") String endDate, 
            @RequestParam(value = "endTime") String endTime,					
		Model model) throws ParseException {
		System.out.println(startTime+" "+endTime);
		Date strDate = new SimpleDateFormat("yyy-MM-dd", Locale.ENGLISH).parse(startDate);		
	//	strDate.setTime(time));
		Date edDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(endDate);		
			Event noah = pplServ.createEntity(new Event(eventName, strDate, edDate));
			model = noah.getModel(model);	
        // model.addAttribute("displayName", noah.getDisplayName());
       	// model.addAttribute("nodeType", noah.getNodeType());
       //	 model.addAttribute("nodeId", noah.getNodeId().toString());
		return "result";
		}
	
	@RequestMapping(value = "create/project", method = RequestMethod.GET)
	public String startProject(Model model) {
		return "create/project";
		}
	
	@RequestMapping(value = "create/addProject", method = RequestMethod.GET)
	public String addProject(
			@RequestParam(value = "projectName") String projectName,
			@RequestParam(value = "startDate") String startDate,
		    @RequestParam(value = "endDate") String endDate,
			@RequestParam(value = "iocDate") String iocDate,					
			Model model) throws ParseException {		
		Date strDate = new SimpleDateFormat("yyy-MM-dd", Locale.ENGLISH).parse(startDate);
		Date edDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(endDate);
		Date ioDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(iocDate);
		System.out.println(startDate+" "+endDate+" "+iocDate);
			Project noah = pplServ.createEntity(new Project(projectName, strDate, edDate, ioDate));
	//		projRepo.save(noah);
	//		Time time = teepRepo.findOne(timeKey);        
	//		time.lastMod(noah, "lastMod");
	//		teepRepo.save(time);
			model = noah.getModel(model);
     //    model.addAttribute("displayName", noah.getDisplayName());
     //  	 model.addAttribute("nodeType", noah.getNodeType());
     //  	 model.addAttribute("nodeId", noah.getNodeId().toString());
		return "result";
		}
	
	@RequestMapping(value = "create/ppl", method = RequestMethod.GET)
	public String startPerson() {
		logger.info("Loacting Create ");
		return "create/ppl";
		}
	
    @RequestMapping(value = "create/addPpl", method = RequestMethod.POST)
    public String addPerson(
            @RequestParam(value = "age") int age,
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            Model model) {				    
		 People noah = pplServ.createEntity(new People(age, firstName, lastName));
		model = noah.getModel(model);
         //   model.addAttribute("displayName", noah.getDisplayName());
         //   model.addAttribute("firstName", noah.getFirstName());
       	 //   model.addAttribute("age", noah.getAge().toString());
       	 //   model.addAttribute("lastName", noah.getLastName());
       	 //   model.addAttribute("nodeId", noah.getNodeId().toString());
       	 //   model.addAttribute("nodeType", noah.getNodeType());
       	 //   model.addAttribute("lastModified", noah.getLastModified().toString());
       	 //   model.addAttribute("firstCreated", noah.getFirstCreated().toString());       	         	   
            return "result";       
    }

    /**
     * 	Handles listing out the existing Nodes
     * 
     * 
     */   
	@RequestMapping(value = "/list/", method = RequestMethod.GET)
	public String list() {
		return "list/list";
		}
	@RequestMapping(value = "/list/{nodeType}/{pageNum}", method = RequestMethod.GET, headers = "Accept=text/html")
    public String listEntities(Model model,
    		@PathVariable Integer pageNum,
    		@PathVariable String nodeType
    		) {
    		Page entityPage;
    		if (nodeType.contains("ppl")){
    			 entityPage = peepRepo.findAll(new PageRequest(pageNum,20));
    		   }
    		   else if(nodeType.contains("org")){
    		    entityPage = orgRepo.findAll(new PageRequest(pageNum,20));  
    		   }
    		   else if(nodeType.contains("event")){
    			entityPage = eventRepo.findAll(new PageRequest(pageNum,20));
    		   }
    		   else if(nodeType.contains("project")){
    			entityPage = projRepo.findAll(new PageRequest(pageNum,20)); 
    		   }
    		   else {
    			   return "result";
    		   }
            int current = entityPage.getNumber();
            int begin = Math.max(0, current - 5);
            int end = Math.min(begin + 10, entityPage.getTotalPages());            
            model.addAttribute("people", entityPage.getContent());
            model.addAttribute("beginIndex", begin);
            model.addAttribute("nodeType", nodeType);
            model.addAttribute("endIndex", end);
            model.addAttribute("currentIndex", current);
            model.addAttribute("totalPages", entityPage.getTotalPages());
        return "/list/page";
    }

    /**
     * 	Handles querying nodes
     * 
     * 
     */ 
	
	@RequestMapping(value = "/query/*", method = RequestMethod.GET)
	public String query() {
		return "query/query";
		}
 
   
    @RequestMapping(value = "/query/{nodeType}/{pageNum}{noah}", method = RequestMethod.GET, headers = "Accept=text/html")
    public String queryEntities(Model model,
    		@PathVariable Integer pageNum,
    		@RequestParam(value = "q") String query,
    		@PathVariable String nodeType,
    		@PathVariable String noah
    		) {
    		Page entityPage;
    	
    		if (nodeType.contains("ppl")){
    			entityPage = peepRepo.findByDisplayName(query, new PageRequest(pageNum,20)) ;
    		   }
    		   else if(nodeType.contains("org")){
    		    entityPage = orgRepo.findByDisplayName(query, new PageRequest(pageNum,20)) ; 
    		   }
    		   else if(nodeType.contains("event")){
    			entityPage = eventRepo.findByDisplayName(query, new PageRequest(pageNum,20)) ;
    		   }
    		   else if(nodeType.contains("project")){
    			entityPage = projRepo.findByDisplayName(query, new PageRequest(pageNum,20)) ;
    		   }
    		   else {
    			   return "result";
    		   }
    		String persist = "?q="+query+"&Search=Submit";
            int current = entityPage.getNumber();
            int begin = Math.max(1, current - 5);
            int end = Math.min(begin + 10, entityPage.getTotalPages());            
            model.addAttribute("people", entityPage.getContent());
            model.addAttribute("beginIndex", begin);
            model.addAttribute("nodeType", nodeType);
            model.addAttribute("search", persist);
            model.addAttribute("endIndex", end);
            model.addAttribute("currentIndex", current);
            model.addAttribute("q", query);
            model.addAttribute("totalPages", entityPage.getTotalPages());
        return "/query/page";
    }
    @RequestMapping(value = "/node/{nodeType}/{nodeId}", method = RequestMethod.GET)


    /**
     * 	Handles single node view requests
     * 
     * 
     */ 
    public String viewNode(
			@PathVariable long nodeId,
			@PathVariable String nodeType, Model model) {
    	
    	if (nodeType.contains("ppl")){
			 People modelNode = peepRepo.findOne(nodeId);
			 model = modelNode.getModel(model);
		   }
		   else if(nodeType.contains("org")){
		   Organization modelNode = orgRepo.findOne(nodeId);
		   model = modelNode.getModel(model);
		   }
		   else if(nodeType.contains("event")){
			   Event modelNode = eventRepo.findOne(nodeId);
			  // model = modelNode.getModel(model);
		   }
		   else if(nodeType.contains("project")){
			Project modelNode = projRepo.findOne(nodeId);
			model = modelNode.getModel(model);
		   }
		   else {
			   return "/indexAccess";
		   }
    
		return "result";
		}
   
	@RequestMapping(value = "/delete/{nodeId}", method = RequestMethod.GET)
	public String deleteNode(@PathVariable long nodeId, Model model) {
		People person = peepRepo.findOne(nodeId);
		peepRepo.delete(person);
		model.addAttribute("people", "Node Deleted!");
		return "result";
		}
	

	@RequestMapping(value = "/initialize", method = RequestMethod.GET)
	public String initialize(Model model) {
		Date date = new Date();
		People pplL = new People(23, "Noah", "Clark");
		peepRepo.save(pplL);
		System.out.println(pplL.getNodeId());
		People pplH = new People(44, "Agustin","Taveras");
		peepRepo.save(pplH);
		Organization org = new Organization("Dept of Denfese","DoD");
		orgRepo.save(org);
		Event event = new Event("Whatever",date, date);
		eventRepo.save(event);
		Project prj= new Project("who cares", date, date, date);
		projRepo.save(prj);	
		pplH.setWorksAt(org);
		org.setOwns(prj);
		org.setWorksAt(pplH);
		org.setPrimeCtrFor(prj);
		event.setHosting(org);
	   pplH.setFriendsWith(pplL);   
	   orgRepo.save(org);
	   eventRepo.save(event);
	   peepRepo.save(pplH);
		return "/indexAccess";
	}




}
