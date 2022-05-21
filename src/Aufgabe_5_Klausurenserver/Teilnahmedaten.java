package Aufgabe_5_Klausurenserver;

import java.util.*;

public class Teilnahmedaten {
    private final Map<String, Set<Integer>> teilnahmen;
    private final FileManager fm;

    public Teilnahmedaten(FileManager _fm)
    {
    	this.fm = _fm;
        this.teilnahmen = fm.loadFile();
    }

    public Set<String> getAllEmails()
    {
        return this.teilnahmen.keySet();
    }

    public Set<Integer> put(String email, Set<Integer> klausurenIDs)
    {
        Set<Integer> alteTeilnahmen = this.teilnahmen.put(email, klausurenIDs);
        fm.saveFile(teilnahmen);
    
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
        fm.saveFile(teilnahmen);
        
        return returnTreesetIfNotNull(alteTeilnahmen);
    }

    private static boolean containsTeilnahmen(Set<Set<Integer>> alleTeilnahmen, Set<Integer> teilnahmen) {
        boolean contains;
        for(Set<Integer> addedTeilnahmen : alleTeilnahmen) {
            if(addedTeilnahmen != teilnahmen) {
                contains = true;
                for (int id : teilnahmen) {
                    if (!addedTeilnahmen.contains(id)) {
                        contains = false;
                        break;
                    }
                }
                if (contains)
                    return true;
            }
        }
        return false;
    }

    private static Set<Set<Integer>> removeDoublets(Set<Set<Integer>> alleTeilnahmen) {
        Set<Set<Integer>> result = new HashSet<>(alleTeilnahmen);
        for(Set<Integer> teilnahmen : alleTeilnahmen) {
            if(containsTeilnahmen(alleTeilnahmen, teilnahmen)) {
                result.remove(teilnahmen);
            }
        }
        return result;
    }

    public Set<Set<Integer>> getAll()
    {
        Set<Set<Integer>> alleTeilnahmen = new HashSet<>();

        for(Set<Integer> teilnahmen : this.teilnahmen.values())
        {
            if(!containsTeilnahmen(alleTeilnahmen,teilnahmen))
                alleTeilnahmen.add(teilnahmen);
        }

        return removeDoublets(alleTeilnahmen);
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
