/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.sbs;

import com.banking.entities.Credit;
import javax.persistence.EntityManager;

/**
 *
 * @author maradona
 */
public class CreditFacade extends AbstractFacade<Credit> {
    
//private EntityManager em;

    public CreditFacade() {
        super(Credit.class);
    }
}
