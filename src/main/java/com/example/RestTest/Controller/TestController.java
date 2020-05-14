package com.example.RestTest.Controller;


import com.example.RestTest.Exceotions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class TestController {
    private int couter = 4;
    private List<Map<String, String>> doList = new ArrayList<Map<String, String>>(){{
     add(new HashMap<String, String>(){{put("id", "1");put("text","First thing");}});
     add(new HashMap<String, String>(){{put("id", "2");put("text","Second thing");}});
     add(new HashMap<String, String>(){{put("id", "3");put("text","Third thing");}});
    }};

    @GetMapping
    public List<Map<String, String>> doList(){
        return doList;
    }

    @GetMapping("{id}")
    public Map<String,String> getOne(@PathVariable String id){
        return getdoList(id);
    }

    private Map<String, String> getdoList(@PathVariable String id) {
        return doList.stream()
                .filter(doList -> doList.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String>create(@RequestBody Map<String, String> todo){
        todo.put("id", String.valueOf(couter++));
        doList.add(todo);
        return todo;

    }
    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> todo){
        Map<String, String> todoFromDb = getdoList(id);
        todoFromDb.putAll(todo);
        todoFromDb.put("id", id);

        return todoFromDb;

    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Map<String, String> todo = getdoList(id);
        doList.remove(todo);


    }

}
