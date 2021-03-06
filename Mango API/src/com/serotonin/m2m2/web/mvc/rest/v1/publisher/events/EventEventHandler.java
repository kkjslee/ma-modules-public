/**
 * Copyright (C) 2015 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.serotonin.m2m2.web.mvc.rest.v1.publisher.events;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.vo.User;
import com.serotonin.m2m2.web.mvc.rest.v1.model.events.EventEventTypeEnum;
import com.serotonin.m2m2.web.mvc.rest.v1.model.events.EventRegistrationModel;
import com.serotonin.m2m2.web.mvc.websocket.MangoWebSocketErrorType;
import com.serotonin.m2m2.web.mvc.websocket.MangoWebSocketHandler;

/**
 * @author Terry Packer
 *
 */
public class EventEventHandler extends MangoWebSocketHandler {
	
	private static final Log LOG = LogFactory.getLog(EventEventHandler.class);
	
	//Map of UserID to User Event Listener
	private final Map<Integer, EventWebSocketPublisher> map = new HashMap<Integer, EventWebSocketPublisher>();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public EventEventHandler(){
		super();
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		
		try {
			User user = this.getUser(session);
			if(user == null){
				//Not Logged In so no go
				this.sendErrorMessage(session, MangoWebSocketErrorType.NOT_LOGGED_IN, new TranslatableMessage("rest.error.notLoggedIn"));
				return;
			}	
			EventRegistrationModel model = this.jacksonMapper.readValue(message.getPayload(), EventRegistrationModel.class);
			
			EventWebSocketPublisher pub = null;
			lock.readLock().lock();
			try{
				pub = map.get(user.getId());
			}finally{
				lock.readLock().unlock();
			}
			
			if (pub != null) {
			    List<String> levels = model.getLevels();
			    List<EventEventTypeEnum> events = model.getEventTypes();
			    if (levels.isEmpty() || events.isEmpty()) {
					lock.writeLock().lock();
					try{
						map.remove(user.getId());
					}finally{
						lock.writeLock().unlock();
					}
					pub.terminate();
			    }
			    else {
                    pub.changeLevels(levels);
                    pub.changeEvents(events);
			    }
			} else {
				pub = new EventWebSocketPublisher(user, model.getLevels(), model.getEventTypes(), session, this.jacksonMapper);
				pub.initialize();
				lock.writeLock().lock();
				try{
					map.put(user.getId(), pub);
				}finally{
					lock.writeLock().unlock();
				}
			}
		
		} catch (Exception e) {
			try {
				this.sendErrorMessage(session, MangoWebSocketErrorType.SERVER_ERROR, new TranslatableMessage("rest.error.serverError", e.getMessage()));
			} catch (Exception e1) {
				LOG.error(e.getMessage(), e);
			}
		} 
		if(LOG.isDebugEnabled())
			LOG.debug(message.getPayload());
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) {

		lock.writeLock().lock();
		try{
			Iterator<Integer> it = map.keySet().iterator();
			while (it.hasNext()) {
				Integer id = it.next();
				EventWebSocketPublisher pub = map.get(id);
				pub.terminate();
			}
			map.clear();
		}finally{
			lock.writeLock().unlock();
		}
		// Handle closing connection here
		if(LOG.isDebugEnabled())
			LOG.debug("Sesssion closed: " + status.getReason());
	}

}
