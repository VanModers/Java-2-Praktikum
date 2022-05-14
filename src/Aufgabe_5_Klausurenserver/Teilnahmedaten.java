package Aufgabe_5_Klausurenserver;

import java.util.*;

public class Teilnahmedaten {
    Map<String, Set<Integer>> teilnahmen;

    public Teilnahmedaten()
    {
        this.teilnahmen = new HashMap<>();
    }

    public Set<String> getAllEmails()
    {
        return this.teilnahmen.keySet();
    }

    public Set<Integer> put(String email, Set<Integer> klausurenIDs)
    {
        Set<Integer> alteTeilnahmen = this.teilnahmen.put(email, klausurenIDs);

        return returnTreesetIfNotNull(alteTeilnahmen);
    }

    public Set<Integer> get(String email)
    {
        Set<Integer> teilnahmen = this.teilnahmen.get(email);

        return returnTreesetIfNotNull(teilnahmen);
    }

    public Set<Integer> del(String email)
    {
        Set<Integer> alteTeilnahmen = this.teilnahmen.remove(email);

        return returnTreesetIfNotNull(alteTeilnahmen);
    }

    private static boolean containsTeilnahmen(Set<Set<Integer>> alleTeilnahmen, Set<Integer> teilnahmen) {
        boolean contains;
        for(Set<Integer> addedTeilnahmen : alleTeilnahmen) {
            contains = true;
            for(int id : teilnahmen) {
                if (!addedTeilnahmen.contains(id)){
                    contains = false;
                    break;
                }
            }
            if(contains)
                return true;
        }
        return false;
    }

    public Set<Set<Integer>> getAll()
    {
        Set<Set<Integer>> alleTeilnahmen = new HashSet<>();

        for(Set<Integer> teilnahmen : this.teilnahmen.values())
        {
            if(!containsTeilnahmen(alleTeilnahmen,teilnahmen))
                alleTeilnahmen.add(teilnahmen);
        }

        return alleTeilnahmen;
    }

    public boolean containsKey(String key) {
        return teilnahmen.containsKey(key);
    }

    private static Set<Integer> returnTreesetIfNotNull(Set<Integer> set)
    {
        if(set != null)
        {
            return new TreeSet<>(set);
        }
        else
        {
            return null;
        }
    }

}
