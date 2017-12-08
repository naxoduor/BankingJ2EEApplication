/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.sbs;

/**
 *
 * @author maradona
 */
import com.banking.entities.Owner;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

public class OwnerFacade extends AbstractFacade<Owner> {
    
 private EntityManager em;
 
 final static Logger logger = Logger.getLogger(OwnerFacade.class);

    public OwnerFacade() {
        super(Owner.class);
    }
}
