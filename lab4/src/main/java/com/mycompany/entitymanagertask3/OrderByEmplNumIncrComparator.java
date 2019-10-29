/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask3;

import com.mycompany.entitymanagertask3.Entities.Group;
import java.util.Comparator;

/**
 *
 * @author Artur
 */
public class OrderByEmplNumIncrComparator implements Comparator<Group>{

    @Override
    public int compare(Group o1, Group o2) {
        int size1=o1.getEmpls().size();
        int size2=o2.getEmpls().size();
        if (size1==size2)
            return 0;
        else
        {
            if (size1>size2)
                return 1;
            else
                return -1;
        }
    }
    
}
