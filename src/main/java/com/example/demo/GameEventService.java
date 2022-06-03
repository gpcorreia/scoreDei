package com.example.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.data.GameEvent;
import com.example.data.Player;
import com.example.data.PlayerEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameEventService {
    @Autowired    
    private GameEventRepository gameEventRepository;    
    
    @Autowired    
    private PlayerEventRepository playerEventRepository;    

 
    public void create_gameEvent(GameEvent gameEvent)  
    {   
        gameEvent.setData(Calendar.getInstance().getTime());

        if(gameEvent.getG().getStatus().compareTo("terminado") == 0)
            return;
        
        if(gameEvent.getEvento().compareTo("terminado") != 0)
            gameEvent.getG().setStatus(gameEvent.getEvento());
        
        if(gameEvent.getEvento().compareTo("terminado") == 0){

            if(gameEvent.getG().getGoloCasa() > gameEvent.getG().getGoloFora()){
                gameEvent.getG().getEquipaCasa().setVitorias(1);
                gameEvent.getG().getEquipaFora().setDerrotas(1);
                gameEvent.getG().getEquipaCasa().setPoints(2);
            }
           
            else if(gameEvent.getG().getGoloCasa() < gameEvent.getG().getGoloFora()){
                gameEvent.getG().getEquipaFora().setVitorias(1);
                gameEvent.getG().getEquipaCasa().setDerrotas(1);
                gameEvent.getG().getEquipaFora().setPoints(2);

            }

            else if(gameEvent.getG().getGoloCasa() == gameEvent.getG().getGoloFora()){
                gameEvent.getG().getEquipaFora().setEmpates(1);
                gameEvent.getG().getEquipaCasa().setEmpates(1);
                gameEvent.getG().getEquipaCasa().setPoints(1);
                gameEvent.getG().getEquipaFora().setPoints(1);

            }

            for(Player p : gameEvent.getG().getEquipaCasa().getListPlayers()){
                p.setMedgoals(p.getGoals()/gameEvent.getG().getEquipaCasa().getNumGames());
            }
            
            for(Player p : gameEvent.getG().getEquipaFora().getListPlayers()){
                p.setMedgoals(p.getGoals()/gameEvent.getG().getEquipaFora().getNumGames());
            }

            gameEvent.getG().setStatus(gameEvent.getEvento());
        }
        gameEventRepository.save(gameEvent);
    }


    public List<String> all_events(String name){
        List<PlayerEvent> listPE = new ArrayList<>();
        playerEventRepository.player_event_order(name).forEach(listPE :: add); 
        
        List<GameEvent> listGE = new ArrayList<>();
        gameEventRepository.game_event_order(name).forEach(listGE :: add); 


        List<String> allEvents = new ArrayList<String>();
        int j = 0;

        for(int i = 0;i<listGE.size();i++){
            if(listPE.size()!=0){
                while(listPE.get(i).getData().compareTo(listPE.get(j).getData()) > 0){
                    allEvents.add(listPE.get(i).toString()+"\n");
                    listPE.remove(listPE.get(i));
                }
                allEvents.add(listGE.get(i).toString()+"\n");
            }
            else if(listPE.size()==0)
                allEvents.add(listGE.get(i).toString()+"\n");
            
        }


        // for(String x : allEvents){
        //     System.out.print(x);
        // }


        return allEvents;
    }
}

