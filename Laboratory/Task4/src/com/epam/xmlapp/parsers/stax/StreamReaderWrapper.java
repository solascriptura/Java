/**
 * 
 */
package com.epam.xmlapp.parsers.stax;

import java.util.List;

import javax.xml.stream.XMLStreamReader;

import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.model.Product;
import com.epam.xmlapp.model.Subcategory;
import com.epam.xmlapp.parsers.ElementEnum;

/**
 * The class contains methods which allow to pack XMLStreamReader object
 * and provide data fields for its work.
 * @author Andrei_Ilyin1
 */
public final class StreamReaderWrapper {  
    /**
     * Pointer to reader object.
     */
    private XMLStreamReader reader;     
    /**
     * Current category.
     */
    private Category category;
    /**
     * Current sub category.
     */
    private Subcategory subcategory;
    /**
     * Current product.
     */
    private Product product;
    /**
     * Current element as enumeration.
     */
    private ElementEnum element;
    /**
     * List of categories.
     */
    private List<Category> categories;
    /**
     * List of sub categories.
     */
    private List<Subcategory> subcategories;
    /**
     * List of products.
     */
    private List<Product> products;
    
    /**
     * Constructor with wrapped parameter
     * @param reader - XMLStreamReader reader
     */
    public StreamReaderWrapper(XMLStreamReader reader) {
	this.reader = reader;	
    }
        
    /**
     * Returns the XMLStreamReader object
     * @return the reader
     */
    public final XMLStreamReader getReader() {
        return reader;
    }
    
    /**
     * Returns the Category object
     * @return the category
     */
    public final Category getCategory() {
        return category;
    }
    
    /**
     * Returns the Subcategory object
     * @return the subcategory
     */
    public final Subcategory getSubcategory() {
        return subcategory;
    }
    
    /**
     * Returns the Product object
     * @return the product
     */
    public final Product getProduct() {
        return product;
    }
    
    /**
     * Returns the ElementEnum
     * @return the element
     */
    public final ElementEnum getElement() {
        return element;
    }
    
    /**
     * Returns the categories list
     * @return the categories
     */
    public final List<Category> getCategories() {
        return categories;
    }
    
    /**
     * Returns the subcategories list
     * @return the subcategories
     */
    public final List<Subcategory> getSubcategories() {
        return subcategories;
    }
    
    /**
     * Returns the products list
     * @return the products
     */
    public final List<Product> getProducts() {
        return products;
    }
    
    /**
     * Sets the category
     * @param category the category to set
     */
    public final void setCategory(Category category) {
        this.category = category;
    }
    
    /**
     * Sets the subcategory
     * @param subcategory the subcategory to set
     */
    public final void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }
    
    /**
     * Sets the product
     * @param product the product to set
     */
    public final void setProduct(Product product) {
        this.product = product;
    }
    
    /**
     * Sets the element
     * @param element the element to set
     */
    public final void setElement(ElementEnum element) {
        this.element = element;
    }
    
    /**
     * Sets the categories list
     * @param categories the categories to set
     */
    public final void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    /**
     * Sets the subcategories list
     * @param subcategories the subcategories to set
     */
    public final void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }
    
    /**
     * Sets the products list
     * @param products the products to set
     */
    public final void setProducts(List<Product> products) {
        this.products = products;
    }
}
