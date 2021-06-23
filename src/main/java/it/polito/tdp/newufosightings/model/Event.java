package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	
	enum EventType{
		DOWN,
		UP1,
		UP2;
		
		
	}
	
	private EventType type;
	private State state;
	
	
	public Event(EventType type, LocalDateTime day, State s) {
		super();
		this.type = type;
		this.day = day;
		this.state=s;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public LocalDateTime getDay() {
		return day;
	}
	public void setDay(LocalDateTime day) {
		this.day = day;
	}
	private LocalDateTime day;
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
	return day.compareTo(o.day);
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	

}
