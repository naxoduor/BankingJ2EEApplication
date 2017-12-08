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
//import org.hibernate.annotations.BatchSize;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
//import javax.validation.constraints,NotNull;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Owner implements Serializable {
    
 private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "NAME")
    private String name;


    @Column(name = "description")
    private String description;
    
    @Column(name="CITY")
    private String city;
    
    @OneToMany(mappedBy="owner", cascade=CascadeType.ALL)
    private List<Car> carlist;
    
    @OneToMany(mappedBy="owner", cascade=CascadeType.ALL)
    private List<Debit> debitlist;
    
    @OneToMany(mappedBy="owner", cascade=CascadeType.ALL)
    private List<Credit> creditlist;

 // @OneToMany(mappedBy = "ownerId")
   //private Collection<Car> carCollection;

    public Owner() {

    }

    public Owner(Long id) {

        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String  getName() {

        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCity() {
    
        return city;
    }
    
    public void setCity(String city) {
        this.city=city;
    }
    
    public List getCarlist() {
    return carlist;
    }
    
    public void setCarlist(List<Car> carlist) {
    this.carlist = carlist;
    }
    
    public List getCreditList() {
    return creditlist;
    }
    
    public void setCreditList(List<Credit> creditlist) {
    this.creditlist = creditlist;
    }
    
    public List getDebitList() {
    return debitlist;
    }
    
    public void setDebitList(List<Debit> debitlist) {
    this.debitlist = debitlist;
    }

    //@XmlTransient
   // public Collection<Car> getCarCollection() {
    //  return carCollection;
  //  }

   // public void setCarCollarection(Collection<Car> carCollection) {
    //    this.carCollection = carCollection;
   // }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Owner)) {
            return false;
        }

        Owner other = (Owner) object;
        if ((this.id == null && other.id != null || (this.id != null && !this.id.equals(other.id)))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "com.journaldev.entities.Owner[ id=" + id + "]";
    }
}

