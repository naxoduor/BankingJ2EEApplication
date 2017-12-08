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
import com.banking.entities.Car;
import com.banking.entities.Owner;
import com.banking.jsfs.util.JSFUtil;
import com.banking.helper.PaginationHelper;
import com.banking.sbs.CarFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

@ManagedBean(name="carController")
@Named(value="carController")
@SessionScoped
public class CarController implements Serializable {
    
 private Car current;
 public Owner owner=null;
    private DataModel items = null;
    //@EJB
    //private @Inject
    CarFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    Logger logger = Logger.getLogger(CarController.class);

    public CarController() {
    }

    public Car getSelected() {
        if (current == null) {
            current = new Car();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CarFacade getFacade() {
        ejbFacade= new CarFacade();
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
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
        current = (Car) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        logger.info("Inside CarController PrepareCreate");
        current = new Car();
        selectedItemIndex = -1;
        logger.info("return CreateCar page");
        return "CreateCar";
       
    }
    
    public void prepareCreateCar(Owner owner) {
        logger.info("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        this.owner = owner;
        logger.info(owner.getName());
        logger.info(owner.getTitle());
        logger.info(owner.getDescription());
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
       
    }
    
    public Owner gettheOwner() {
        logger.info("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        logger.info(owner.getName());
        logger.info(owner.getDescription());
    return owner;
    }

    public String create() {
        try {
            logger.info("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
             current.setOwner(gettheOwner());
            logger.info(gettheOwner().getName());
            logger.info(gettheOwner().getTitle());
            //getSelected().setOwner(getOwner());
            getFacade().create(current);
            JSFUtil.addSuccessMessage(ResourceBundle.getBundle("Messages").getString("fine"));
            return prepareCreate();
        } catch (Exception e) {
            JSFUtil.addErrorMessage(e, ResourceBundle.getBundle("Messages").getString("best"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Car) getItems().getRowData();
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
        current = (Car) getItems().getRowData();
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

    public Car getCar(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Car.class)
    public static class CarControllerConverter implements Converter {

        // @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CarController controller = (CarController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "carController");


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
            if (object instanceof Car) {
                Car o = (Car) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " +object.getClass().getName() + "; expected type: " + Car.class.getName());
            }
        }
    }
}


