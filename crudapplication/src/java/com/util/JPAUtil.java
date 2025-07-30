
package com.util;

import javax.persistence.*;
public class JPAUtil {
   
     private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
