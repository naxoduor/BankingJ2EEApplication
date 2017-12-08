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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
public class Car implements Serializable {
    
 private static final long serialVersionUID = 1L;
    
@Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;
    @Column(name="KILOMETERS")
    private Integer kilometers;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "NUMBERPLATE")
    
    private String numberPlate;
    @ManyToOne
    @JoinColumn(name="owner")
    private Owner owner;

    public Car() {

    }

    public Car(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
    return title;
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
    
        if (!(object instanceof Car)) {
        return false;
        }
        
        Car other = (Car) object;
        if ((this.id == null && other.id !=null) || (this.id !=null && !this.id.equals(other.id))) {
        return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
    return "com.journaldev.entities.Car[id=" + id + "]";
    }
    
}

