package com.example.demo;


import java.util.List;

import com.example.data.GameEvent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GameEventRepository extends CrudRepository<GameEvent,Integer>{
    @Query("SELECT ge FROM GameEvent ge where ge.g.nameGame like %?1 ORDER BY data ASC")
    List<GameEvent> game_event_order(String name);
}