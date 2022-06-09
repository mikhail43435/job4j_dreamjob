package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;

import java.util.*;

@Service
@ThreadSafe
public class CityService {

    private final Map<Integer, City> store = new HashMap<>();

    public CityService() {
        store.put(0, new City(0, "<>"));
        store.put(1, new City(1, "Москва"));
        store.put(2, new City(2, "СПб"));
        store.put(3, new City(3, "Екб"));
    }

    public List<City> getAllCities() {
        return new ArrayList<>(store.values());
    }

    public City findById(int id) {
        return store.get(id);
    }
}