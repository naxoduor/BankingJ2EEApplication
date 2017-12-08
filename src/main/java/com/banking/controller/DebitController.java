/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.controller;

import com.banking.sbs.DebitFacade;
import com.banking.entities.Debit;
import com.banking.entities.Owner;
import com.banking.helper.PaginationHelper;
import com.banking.jsfs.util.JSFUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.apache.log4j.Logger;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.context.ExternalContext;

/**
 *
 * @author maradona
 */
@ManagedBean(name="debitController")
@Named(value="debitController")
@SessionScoped
public class DebitController implements Serializable {
    
private Debit current;
 public Owner owner=null;
    private DataModel items = null;
    private Long idn;
    //@EJB
    //private @Inject
    DebitFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    Logger logger = Logger.getLogger(DebitController.class);

    public DebitController() {
    }

    public Debit getSelected() {
        if (current == null) {
            current = new Debit();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DebitFacade getFacade() {
        ejbFacade= new DebitFacade();
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        ArrayList<Debit> list = new ArrayList<Debit>();
        if (pagination == null) {
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
                    String squery="SELECT c FROM Debit c WHERE c.owner.id = :ownerId";
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
        current = (Debit) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    
    public String prepareCreate() {
        logger.info("Inside CreditController PrepareCreate");
        current = new Debit();
        selectedItemIndex = -1;
        recreateModel();
        recreatePagination();
        getPagination();
        return "DebitList";
    }

    
    public String setOwner(Owner owner) {
        logger.info("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        this.owner = owner;
        logger.info(owner.getName());
        logger.info(owner.getTitle());
        logger.info(owner.getDescription());
        recreatePagination();
        recreateModel();
       //current.setOwner(owner);
       //getSelected().setOwner(owner);
       //logger.info("the car title is");
       //logger.info(getSelected().getTitle());
       //logger.info("the distance travelled is");
       //logger.info(getSelected().getKilometers());
       //logger.info("the owners discription is");
       //logger.info(getSelected().getOwner().getDescription());
       //logger.info("the owners name is");
       //logger.info(getSelected().getOwner().getName());
       // selectedItemIndex = -1;
        logger.info("return CreateCar page");
       return "DebitList";
    }
    
    
    public Owner gettheOwner() {
        logger.info("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
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
        current = (Debit) getItems().getRowData();
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
        current = (Debit) getItems().getRowData();
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

    public Debit getDebit(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Debit.class)
    public static class DebitControllerConverter implements Converter {

        // @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DebitController controller = (DebitController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "debitController");


            return controller.getDebit(getKey(value));
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
            if (object instanceof Debit) {
                Debit o = (Debit) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " +object.getClass().getName() + "; expected type: " + Debit.class.getName());
            }
        }
    }
}



