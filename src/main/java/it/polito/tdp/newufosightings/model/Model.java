package it.polito.tdp.newufosightings.model;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;
import it.polito.tdp.newufosightings.model.Event.EventType;

public class Model {
	
	NewUfoSightingsDAO dao= new  NewUfoSightingsDAO();
	Graph <State, DefaultWeightedEdge> grafo;
	Map <String, State> idMap;
	List <State> stati;
	
	PriorityQueue<Event> queue;
	int anno;
	String forma;
	
	Duration d1 ;
	
	
	
	public void init(int anno, String forma, int delta) {
		this.anno=anno;
		this.forma=forma;
		d1 = Duration.ofDays(delta);
		stati= new LinkedList<>();
		queue = new PriorityQueue<Event>();
		
		for(Sighting s:dao.loadS(anno, forma, idMap)) {
			queue.add(new Event (EventType.DOWN, s.getDatetime(), idMap.get(s.getState().toUpperCase())));
			
			
		}
		
		
	}
	
	public void run() {
		
		while(!queue.isEmpty()){
			Event e= queue.poll();
			simula(e);
			
		}
		
	}
	
	public void simula(Event e) {
		switch(e.getType()) {
		
		case DOWN:
			double random = Math.random();
			if(random<0.5) {
				for(State s: Graphs.neighborListOf(grafo, e.getState())) {
					s.setDefCon(s.getDefCon()-0.5);
					queue.add(new Event(EventType.UP1, e.getDay().plus(d1), s));
                   
					
					
				}}
				e.getState().setDefCon(e.getState().getDefCon()-1);
				queue.add(new Event(EventType.UP2, e.getDay().plus(d1), e.getState()));
				
				
		case UP1:
			e.getState().setDefCon(e.getState().getDefCon()+0.5);
				
		case UP2:
			e.getState().setDefCon(e.getState().getDefCon()+1);

		default:
			break;
		
		
		
		}
		
		
		
	}
	
	public String pericolo() {
		String s = "";
		
		for(State s1: idMap.values()) {
			s = s + s1.getName() + " " + s1.getDefCon()+ "\n";
			
			
		}
		return s;
		
		
	}
	
	
	
	
	
	
	
	
	
	public List<String> listaForme(int anno){
		return dao.loadYarShapes(anno);
		
	}
	
	public void creaGrafo(int anno, String forma) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.loadAllStates());
		idMap = new LinkedHashMap<>();
		for(State s:grafo.vertexSet()) {
			idMap.put(s.getId(), s);
			
			for(Arco a: dao.loadArchi(anno, forma, idMap)) {
				if(!grafo.containsEdge(grafo.getEdge(a.getS1(), a.getS2()))&&!grafo.containsEdge(grafo.getEdge(a.getS2(), a.getS1())))
				Graphs.addEdge(grafo, a.getS1(), a.getS2(), a.getPeso());
				
			}
			
		}}
		
		public String VA(){
			return "Il numero di vertici e archi is "+ grafo.vertexSet().size()+" " + grafo.edgeSet().size();
			
		}
		
		public String stampaVicini() {
			String s="";
			for(State st: grafo.vertexSet()) {
				double peso=0;
				for(State st2: Graphs.neighborListOf(grafo, st)){
					peso += grafo.getEdgeWeight(grafo.getEdge(st, st2));
					
				}
			
				s = s + st.getName()+ " "+ peso+"\n";
			
			}
			return s;
		}
		
		
		
		
		
	
	

}
