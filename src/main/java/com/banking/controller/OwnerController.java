/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.controller;

/**
 *
 * @author maradona
 */
import com.banking.entities.Owner;
import com.banking.entities.Car;
import com.banking.jsfs.util.JSFUtil;
import com.banking.helper.PaginationHelper;
import com.banking.sbs.OwnerFacade;
import com.banking.controller.CarController;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
//import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import java.util.List;
import java.util.ArrayList;



import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import javax.ejb.EJB;

@ManagedBean(name="ownerController")
@Named( value ="ownerController")
@ViewScoped
@SessionScoped
public class OwnerController implements Serializable {
    
    //@ManagedProperty(value="#{carController}")
    private CarController carController;
    
private Owner current;
    private DataModel items = null;
    
    
    private OwnerFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    Logger logger = Logger.getLogger(OwnerController.class);
    

   

    public OwnerController() {
        
        
    }

    public Owner getSelected() {
        if (current == null) {
            current = new Owner();
            selectedItemIndex = -1;
        }
        return current;
    }

    private OwnerFacade getFacade() {
        ejbFacade = new OwnerFacade();
        return ejbFacade;
       
    }

    public PaginationHelper getPagination() {
        
        if(pagination==null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel<Owner> createPageDataModel() {
                    List<Owner> list = new ArrayList<Owner>();
                    logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                    logger.info(getPageFirstItem());
                    logger.info(getPageSize());
                     list = getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()});
                     for(Owner owner : list) {
                     logger.info(owner.getName());
                     logger.info(owner.getTitle());
                     logger.info(owner.getDescription());
                     }
             
                    return new ListDataModel<Owner>(getFacade().findAll());
                }
            };
        }
        
        return pagination;
    }
    
    public CarController getCarController() {
    
        return carController;
    }
    
    public void setCarController(CarController carController) {
    this.carController = carController;
    }
    
  // ELContext elContext = FacesContext.getCurrentInstance().getELContext();
    //carController = elContext.getELResolver().getValue(elContext, null, "carController");

    public String prepareList() {
        recreateModel();
        recreatePagination();
        getPagination();
        return "List";
    }

    public String prepareView() {
        logger.info("view");
        current = (Owner) getItems().getRowData();
        logger.info(current.getName());
        logger.info(current.getTitle());
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    
    public void loadedMethod() {
        logger.info("inside loadedMethod");
    }
    
    public String createList() {
    recreateModel();
    recreatePagination();
    return "List";
    }
    
    public String createCar() {
        Owner owner = new Owner();
        logger.info("create car");
        current = (Owner) getItems().getRowData();
        owner = current;
        logger.info(current.getName());
        logger.info(current.getTitle());
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
       //getCarController().prepareCreateCar(current);
       FacesContext facesContext = FacesContext.getCurrentInstance();
       CarController carController = (CarController) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "carController");
       carController.prepareCreateCar(owner);
        return "CreateCar";
    }
    
    public String createCredit() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext ec = facesContext.getExternalContext();
        
        
        Owner owner = new Owner();
        logger.info("create credit in ownercontroller");
        current = (Owner) getItems().getRowData();
        owner = current;
        logger.info(current.getName());
        logger.info(current.getTitle());
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
       //getCarController().prepareCreateCar(current);
       CreditController creditController = (CreditController) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "creditController");
       creditController.setOwner(owner);
       recreateModel();
       recreatePagination();
       getPagination();
       return "CreateCredit";
        
        
        
    }
    
    public String createOwner() {
    return "Create";
    }
    
    public String listCredit() {
        
         FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext ec = facesContext.getExternalContext();
        
        logger.info("create credit in ownercontroller");
        if(current==null){
        current = (Owner) getItems().getRowData();
        }
        logger.info(current.getName());
        logger.info(current.getTitle());
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
       //getCarController().prepareCreateCar(current);
       CreditController creditController = (CreditController) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "creditController");
       creditController.setOwner(current);
       current=null;
        return "CreditList";
    }
    
    public String createDebit() {
        Owner owner = new Owner();
        logger.info("createDebit method");
        current = (Owner) getItems().getRowData();
        owner = current;
        logger.info(current.getName());
        logger.info(current.getTitle());
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
       //getCarController().prepareCreateCar(current);
       FacesContext facesContext = FacesContext.getCurrentInstance();
       DebitController debitController = (DebitController) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "debitController");
       debitController.setOwner(owner);
        return "CreateDebit";
    }
    
    public String listDebit() {
        Owner owner = new Owner();
        logger.info("listDebit method");
        current = (Owner) getItems().getRowData();
        owner = current;
        logger.info(current.getName());
        logger.info(current.getTitle());
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
       //getCarController().prepareCreateCar(current);
       FacesContext facesContext = FacesContext.getCurrentInstance();
       DebitController debitController = (DebitController) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "debitController");
       debitController.setOwner(owner);
        return "DebitList";
    }
    
    public void createNewDataTable() {
        logger.info("createNewDataTable method");
        logger.info("recreate pagination");
    recreatePagination();
    recreateModel();
    
    }

    public String prepareCreate() {
        logger.info("prepare");

        Car car = new Car();
        selectedItemIndex = -1;
        recreateModel();
        recreatePagination();
        getPagination();
        return "List";
    }
    
    public String prepareCreateone() {
        logger.info("prepare createone");

        Car car = new Car();
        selectedItemIndex = -1;
        recreateModel();
        recreatePagination();
        getPagination();
        return "List";
    }

    public String create() {
        try {
           logger.info("create");
           logger.info(getFacade().getClass());
           getFacade().create(current);
            JSFUtil.addSuccessMessage(ResourceBundle.getBundle("Messages").getString("hello"));
            return prepareCreate();
        } catch (Exception e) {
            JSFUtil.addErrorMessage(e, ResourceBundle.getBundle("Messages").getString("good"));
            return null;
        }
    }

    public String prepareEdit() {
       logger.info("prepare edit");
        current = (Owner) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            logger.info("update");
            getFacade().edit(current);
            JSFUtil.addSuccessMessage(ResourceBundle.getBundle("/jsf/Bundle").getString("OwnerUpdated"));
            return "View";
        } catch (Exception e) {
            JSFUtil.addErrorMessage(e, ResourceBundle.getBundle("/jsf/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        logger.info("list");
        current = (Owner) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex > 1) {
            return "View";
        } else {
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JSFUtil.addSuccessMessage(ResourceBundle.getBundle("/jsf/Bundle").getString("OwnerDeleted"));
        } catch (Exception e) {
            JSFUtil.addErrorMessage(e, ResourceBundle.getBundle("/jsf/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            selectedItemIndex = count - 1;

            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }

        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
            
        }
        return items;
    }

   public void recreateModel() {
        logger.info("recreate model in ownercontroller");
        items = null;
    }

    public void recreatePagination() {
        logger.info("recreate pagination in ownercontroller");
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JSFUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JSFUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Owner getOwner(java.lang.Long id) {
        return ejbFacade.find(id);
    }
    
    public String CreateCar() {
        logger.info("inside CreateCar meyhod");
        getCarController().prepareCreate();
        logger.info("return CreateCar page");
        return "CreateCar";
    //CarController carController = CarController.getCurrentInstance();
    //carController.prepareCreate();
     //FacesContext context = FacesContext.getCurrentInstance();
       // carController = (CarController) context.getApplication().getVariableResolver().resolveVariable(context, BEAN_NAME);
       // carController.prepareCreate();
    }

    @FacesConverter(forClass = Owner.class)
    public static class OwnerControllerConverter implements Converter {

       // @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OwnerController controller = (OwnerController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "ownerController");


            return controller.getOwner(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        //@Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Owner) {
                Owner o = (Owner) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " +object.getClass().getName() + "; expected type: " + Owner.class.getName());
            }
        }
    }
}

