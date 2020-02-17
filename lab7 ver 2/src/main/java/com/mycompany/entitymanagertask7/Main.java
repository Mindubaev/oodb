/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7;

import com.mycompany.entitymanagertask7.DAO.DBScanner;
import com.mycompany.entitymanagertask7.MetaClass.EntityMeta;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Artur
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CustomJPAAnnotationProcessor annotationProcessor=new CustomJPAAnnotationProcessor("com.mycompany.entitymanagertask7.Entities");
        List<EntityMeta> metaClasses=annotationProcessor.builtMetaClasses();
        DBScanner scanner=new DBScanner("org.postgresql.Driver","jdbc:postgresql://localhost:5432/postgres2", "postgres", "root");
        HashMap<String,HashSet<String>> tables=scanner.getTablesInfo();
        for (EntityMeta entityMeta:metaClasses)
            if (entityMeta.checkDB(tables))
                System.out.println(entityMeta.getName()+" was succesfully checked");
            else
                System.out.println(entityMeta.getName()+" failed validation");
    }
    
    public static void writeToFile(String str){
        File file =new File("src/metaClasses.txt");
        if (!file.exists())
            try {
                file.createNewFile();
        } catch (IOException ex) {
                System.err.println("Can not create file cause:"+ex );
        }
        try(FileWriter writer=new FileWriter(file,false)){
            writer.write(str);
        } catch (IOException ex) {
            System.err.println("Can not write file cause:"+ex );
        }
    }
    
}
