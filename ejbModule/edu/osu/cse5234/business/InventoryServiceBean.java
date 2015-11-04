package edu.osu.cse5234.business;

import edu.osu.cse5234.business.view.Inventory;
import edu.osu.cse5234.business.view.InventoryService;
import edu.osu.cse5234.business.view.Item;

import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.*;

/**
 * Session Bean implementation class InventoryServiceBean
 */
@Stateless
@Remote(InventoryService.class)
public class InventoryServiceBean implements InventoryService {

    /**
     * Default constructor. 
     */
    public InventoryServiceBean() {
        // TODO Auto-generated constructor stub
    }

    @PersistenceContext private EntityManager entityManager;
    
    private static final String MY_QUERY = "select i from Item i";
//    private Item item;
    private Inventory inventory = new Inventory();
    
//    public void setItemID(int id) {
//    	item = entityManager.find(Item.class, id);	
//    	Inventory inventory = Inventory.getInstance();
//    	inventory.add(item);
//    }
    
	@Override
	public Inventory getAvailableInventory() {
		// TODO Auto-generated method stub
//		Inventory inventory = new Inventory();
		inventory.setItems(entityManager.createQuery(MY_QUERY, Item.class).getResultList());
		return inventory;
	}

	@Override
	public boolean validateQuantity(Collection<Item> items) {
		// TODO Auto-generated method stub
		List<Item> orderItems = (List<Item>) items;
		Inventory inventory_origin = getAvailableInventory();
		for(int i=0; i<orderItems.size(); i++){
			Item orderOne = orderItems.get(i);
			for(int j=0; j<inventory_origin.size(); j++){
				Item inventoryOne = inventory_origin.getInventory().get(j);
				if(orderOne.getId()==inventoryOne.getId()){
					if(Integer.parseInt(orderOne.getQuantity())>Integer.parseInt(inventoryOne.getQuantity())){
						return false;
					}
					break;
				}
			}
		}
		return true;
	}

	@Override
	public boolean updateInventory(Collection<Item> items) {
		// TODO Auto-generated method stub
		// now it is in purchase controller
		return true;
	}
    
    

}
