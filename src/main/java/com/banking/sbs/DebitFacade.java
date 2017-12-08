/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.sbs;

import com.banking.entities.Debit;
import javax.persistence.EntityManager;

/**
 *
 * @author maradona
 */
public class DebitFacade extends AbstractFacade<Debit> {
        
 private EntityManager em;

    public DebitFacade() {
        super(Debit.class);
    }
}

