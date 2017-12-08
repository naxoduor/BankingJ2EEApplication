/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.controller;

import com.banking.entities.Credit;
import com.banking.entities.Owner;
import com.banking.sbs.CreditFacade;
import com.banking.helper.PaginationHelper;
import com.banking.jsfs.util.JSFUtil;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.apache.log4j.Logger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.HashMap;

/**
 *
 * @author maradona
 */
@ManagedBean(name="creditController")
@Named(value="creditController")
@SessionScoped
public class CreditController implements Serializable {
    
 private Credit current;
 public Owner owner=null;
    private DataModel items = null;
    private long idn;
    //@EJB
    //private @Inject
    CreditFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    Logger logger = Logger.getLogger(CreditController.class);

    public CreditController() {
    }

    public Credit getSelected() {
        if (current == null) {
            current = new Credit();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CreditFacade getFacade() {
        ejbFacade= new CreditFacade();
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
                public DataModel createPageDataModel() {
                    logger.info("create page datamodel here");
                    logger.info(gettheOwner().getName());
                    logger.info(gettheOwner().getId());
                    String squery="SELECT c FROM Credit c WHERE c.owner.id = :ownerId";
                    HashMap<String, Object>params= new HashMap<String, Object>();
                    params.put("ownerId", gettheOwner().getId());
                    logger.info(gettheOwner().getName());
                    logger.info(gettheOwner().getTitle());
                    return new ListDataModel(getFacade().findEntries(squery, params));
                }
            };
      }
        
        return pagination;
    }
    
   /* public static CarController getCurrentInstance() {
    
        FacesContext context = FacesContext.getCurrentInstance();
        return (CarController) context.getApplication().getVariableResolver().resolveVariable(context, BEAN_NAME);
    }*/

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Credit) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        logger.info("Inside CreditController PrepareCreate");
        current = new Credit();
        selectedItemIndex = -1;
        recreateModel();
        recreatePagination();
        getPagination();
        return "CreditList";
    }
    
    public Credit getCurrent() {
    return current;
    }
    
   
    
    public String setOwner(Owner owner) {
        logger.info("prepare the create credit controlleraaaaaaaaaaaaaaaaaaaaaa");
        logger.info("set the owner and recreate pagination");
        logger.info("twitter twitter twitter twitter twitter twitter aaaaaaaaaaaaaaa");
        this.owner = owner;
        logger.info(owner.getName());
        logger.info(owner.getTitle());
        logger.info(owner.getDescription());
        recreatePagination();
        recreateModel();
        return "CreditList";
     
    }
    
    public String createCredit(Owner owner) {
        logger.info("Create Credit");
        this.owner=owner;
        return "CreateCredit";
    }
    
    
    public Owner gettheOwner() {
        logger.info("get the owner so as to retrieve credit entries");
        logger.info(owner.getName());
        logger.info(owner.getDescription());
    return owner;
    }
    
    public void setIdn(Long idn) {
    this.idn=idn;
    }
    
    public Long getIdn() {
    return idn;
    }
    
    

    public String create() {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext ec = facesContext.getExternalContext();
        
       
            logger.info("create the credit amount here vvvvvvvvvvvvvvvv");
            logger.info("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
             current.setOwner(gettheOwner());
            current.setDate(LocalDate.now());
            getFacade().create(current);
            
       OwnerController ownerController = (OwnerController) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "ownerController");
                        
   
       return prepareCreate();
      
       
    }
       

    public String prepareEdit() {
        current = (Credit) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JSFUtil.addSuccessMessage(ResourceBundle.getBundle("/jsf/Bundle").getString("OwnerUpdated"));
            return "View";
        } catch (Exception e) {
            JSFUtil.addErrorMessage(e, ResourceBundle.getBundle("/jsf/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Credit) getItems().getRowData();
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

    private void recreateModel() {
        logger.info("recreate model");
        items = null;
    }

    private void recreatePagination() {
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

    public Credit getCar(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Credit.class)
    public static class CreditControllerConverter implements Converter {

        // @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CreditController controller = (CreditController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "creditController");


            return controller.getCar(getKey(value));
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
            if (object instanceof Credit) {
                Credit c = (Credit) object;
                return getStringKey(c.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " +object.getClass().getName() + "; expected type: " + Credit.class.getName());
            }
        }
    }
}



