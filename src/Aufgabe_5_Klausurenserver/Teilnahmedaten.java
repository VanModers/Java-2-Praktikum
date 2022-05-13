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

    //TODO: Verhalten bei Schnittmenge
    public Set<Set<Integer>> getAll()
    {
        Set<Set<Integer>> alleTeilnahmen = new HashSet<>();

        for(Set<Integer> teilnahmen : this.teilnahmen.values())
        {
            alleTeilnahmen.add(teilnahmen);
        }

        return alleTeilnahmen;
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
