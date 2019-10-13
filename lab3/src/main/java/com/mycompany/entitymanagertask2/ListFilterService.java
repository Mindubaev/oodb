/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Artur
 */
public interface ListFilterService {
    
    public static List<Group> filterBy(List<Group> groups,Predicate<Group> predicate){
        List<Group> resultList=new ArrayList<>();
        for (Group group:groups)
            if (predicate.test(group))
                resultList.add(group);
        return resultList;
    }
    
    public static List<Group> orderBy(List<Group> groups,Comparator<Group> comparator){
        Group[] groupsArr=new Group[groups.size()];
        groupsArr=groups.toArray(groupsArr);
        List list =Arrays.stream(groupsArr).sorted(comparator).collect(Collectors.toList());
        return list;
    }
    
}
