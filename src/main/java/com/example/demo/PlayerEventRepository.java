package com.example.demo;

import java.util.List;

import com.example.data.PlayerEvent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlayerEventRepository extends CrudRepository<PlayerEvent,Integer>{
    @Query("SELECT pe FROM PlayerEvent pe where pe.g.nameGame like %?1 ORDER BY data ASC")
    List<PlayerEvent> player_event_order(String name);
}
