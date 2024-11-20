package Shared;

/**
 * Medicine represents the inventory of medicine.It contains the name of the
 * medicine, the stock level, and a stock alert level that triggers a warning
 * when stock levels are low.
 * 
 * @author Maestro
 */
public class Medicine {

	/**
	 * name of the medicine.
	 */
	private String name;
	/**
	 * current stock level of the medicine.
	 */
	private int inStock;
	/**
	 * stock alert level. If stock falls below this level, an alert is triggered.
	 */
	private int stockAlertLevel;

	/**
	 * Medicine constructor
	 * 
	 * @param name            name of the medicine
	 * @param inStock         the stock level of the medicine
	 * @param stockAlertLevel the set low level of the stock
	 */
	public Medicine(String name, int inStock, int stockAlertLevel) {
		this.name = name;
		this.inStock = inStock;
		this.stockAlertLevel = stockAlertLevel;
	}

	/**
	 * getName: returns Medicine name
	 * 
	 * @return Medicine name
	 */
	public String getName() {
		return name;
	}

	/**
	 * getInStock: returns the stock level of the medicine
	 * 
	 * @return stock level of the medicine
	 */
	public int getInStock() {
		return inStock;
	}

	/**
	 * getStockAlertLevel: returns the low stock alert level
	 * 
	 * @return low stock alert level the set low level of the stock
	 */
	public int getStockAlertLevel() {
		return stockAlertLevel;
	}

	/**
	 * setInStock: sets the stock level of the medicine
	 * 
	 * @param inStock the stock level of the medicine
	 */
	public void setInStock(int inStock) {
		this.inStock = inStock;
		if (inStock <= stockAlertLevel) {
			System.out.println("Stock levels are low for " + name + ". Please add more stock.");
		}
	}

	/**
	 * setName: sets the name of the medicine
	 * 
	 * @param name name of the medicine
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * setInStock: sets the low stock alert level of the medicine
	 * 
	 * @param stockAlertLevel set low stock alert of the medicine
	 */
	public void setStockAlertLevel(int stockAlertLevel) {
		this.stockAlertLevel = stockAlertLevel;
	}

	/**
	 * Returns a string representation of the that includes the name, current stock
	 * level, and stock alert level.
	 * 
	 * @return a string representation
	 */

	@Override
	public String toString() {
		return "/n Medicine: " + name + "/n Currently in Stock: " + inStock + "/n Low Stock Alert Level: "
				+ stockAlertLevel;
	}

}
