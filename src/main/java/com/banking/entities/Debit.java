/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.entities;

/**
 *
 * @author maradona
 */
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import java.time.*;
import javax.persistence.CascadeType;

@Entity
public class Debit implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="AMOUNT")
    private Double amount;
    
    @Column(name="DESCRIPTION")
    private String description;
    
    @Column(name="DATE")
    private LocalDate date;
    
 
    
    @ManyToOne(optional=false, cascade=CascadeType.REFRESH)
    @JoinColumn(name="owner")
    private Owner owner;
    
    public Debit() {
    }
    
    public Debit(Long id) {
    this.id = id;
    }
    
    public Long getId() {
    return id;
    }
    
    public void setId(Long id) {
    this.id = id;
    }
    
    public Double getAmount() {
    return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount= amount;
    }
    
    public String getDescription() {
    return description;
    }
    
    public void setDescription(String description) {
    this.description = description;
    }
    
    public LocalDate getDate() {
    return date;
    }
    
    public void setDate(LocalDate date) {
    this.date= date;
    }
    
    public Owner getOwner() {
        return owner;
    }
    
    public void setOwner(Owner owner) {
    this.owner = owner;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash +=(id != null ? id.hashCode() : 0);
        return hash;
    }
    
     @Override
    public boolean equals(Object object) {
        if (!(object instanceof Debit)) {
            return false;
        }

        Debit other = (Debit) object;
        if ((this.id == null && other.id != null || (this.id != null && !this.id.equals(other.id)))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "com.journaldev.entities.Debit[ id=" + id + "]";
    }
}
