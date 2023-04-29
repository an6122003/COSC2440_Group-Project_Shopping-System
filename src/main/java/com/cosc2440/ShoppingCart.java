package com.cosc2440;

/**
 * @author Nguyen Quoc An - s3938278
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.security.sasl.SaslException;

public class ShoppingCart {
    private final static double BASE_FEE = 0.1;
    private HashMap<Product, Integer> shoppingCart = new HashMap<>();
    private static HashMap<Product, ArrayList<String>> giftMessageList = new HashMap<>();
    private String name; //Assume cart have different and unique name
    private Coupon couponApplied;

    /**
    Creates a new instance of ShoppingCart with an empty   HashMap of products unique name and quantity.
    The name is generated based on the current number of shopping carts in the system.
    @throws Exception if there is an error retrieving the cart count from the ShoppingCartList.
    */
    public ShoppingCart() {
        this.shoppingCart = new HashMap<Product, Integer>();
        this.name = Integer.toString(ShoppingCartList.getCartCount()+1);
        ShoppingCartList.getShoppingCartMap().put(Integer.toString(ShoppingCartList.getCartCount()+1),this);
    }

    /**
    Returns a string representation of the ShoppingCart object, including the cart number, weight, total price, and the products contained in the cart.
    @return a string representation of the ShoppingCart object
    */
    @Override
    public String toString(){
        return String.format("Cart number: %s, Weight: %.1f, Total Price: %.1f, Product: %s, Gift Message: %s, Coupon Applied: %s", this.name, this.calculateWeight(),this.cartAmount(),this.shoppingCart.toString(), this.giftMessageList, couponApplied);
    }

    public void addProductToCart(Product p, int quantity){
        if (shoppingCart.containsKey(p)){
            for (Product product: shoppingCart.keySet()){
                if (product.getName().equals(p)){
                    int currentQuantity = shoppingCart.get(p);
                    shoppingCart.put(p, currentQuantity + quantity);
                }
            }
        }else{
            shoppingCart.put(p, 1);
        }        
    }

    // public static void addProductToCart() throws Exception{
    //     Scanner scanner = new Scanner(System.in);
    //     String name;
    //     String productName;
    //     int quantity;
    //     String giftMessage;
    //     boolean cartFound = false;
    //     System.out.println("Enter Shopping cart name: ");
    //     name = scanner.nextLine();
    //     for (ShoppingCart s: ShoppingCartList.getShoppingCartList()){
    //         if (s.getName().equals(name)){
    //             System.out.println("Enter Product Name to add to shopping cart");
    //             productName = scanner.nextLine();
    //             for (Product p: ProductList.getProductList()){
    //                 if (p.getName().equalsIgnoreCase(productName)){
    //                     if (p instanceof GiftableDigitalProduct | p instanceof GiftablePhysicalProduct){
    //                         System.out.println("Enter the quantity");
    //                         quantity = scanner.nextInt();
    //                         scanner.nextLine();
    //                         for (int i = 0; i < quantity; i++){
    //                             System.out.println("Enter the gift message for "+productName+ " "+ (i+1));
    //                             giftMessage = scanner.nextLine();
    //                             if (s.addItem(productName,1, giftMessage)){
    //                                 System.out.println("Added "+productName+" with the gift message: \"" + giftMessage + "\" to cart: "+s.getName()+"\n");
    //                             }else{
    //                                 System.out.println("Product not found/ not enough quantity.");
    //                             }
    //                         }
    //                         cartFound = true;
    //                         break;
    //                     }else{
    //                         System.out.println("Enter the quantity");
    //                         quantity = scanner.nextInt();
    //                         scanner.nextLine();
    //                         // s.addItem(productName);
    //                         if (s.addItem(productName,quantity)){
    //                             System.out.println("Added "+quantity+" "+productName+" to cart: "+s.getName());
    //                         }else{
    //                             System.out.println("Product not found/ not enough quantity.");
    //                         }
    //                         cartFound = true;
    //                         break;
    //                     }
    //                 } 
    //             }
    //         }
    //     }
    //     if (cartFound == false){
    //         System.out.println("Cart not found.");
    //     }
    // }

    public static void addProductToCart() throws Exception{
        Scanner scanner = new Scanner(System.in);
        String shoppingCartName;
        String productName;
        int quantity;
        String giftMessage;
        boolean cartFound = false;

        System.out.println("Enter Shopping cart name: ");
        shoppingCartName = scanner.nextLine();
        ShoppingCart s = ShoppingCartList.getShoppingCartObjectByName(shoppingCartName);

        System.out.println("Enter Product Name to add to shopping cart");
        productName = scanner.nextLine();
        Product p = ProductList.getProductObjectByName(productName);

        if (p instanceof GiftableDigitalProduct | p instanceof GiftablePhysicalProduct){
            System.out.println("Enter the quantity");
            quantity = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < quantity; i++){
                System.out.println("Enter the gift message for "+productName+ " number "+ (i+1));
                giftMessage = scanner.nextLine();
                if (s.addItem(productName,1, giftMessage)){
                    System.out.println("Added "+productName+" with the gift message: \"" + giftMessage + "\" to cart: "+s.getName()+"\n");
                }else{
                    System.out.println("Product not found/ not enough quantity.");
                }
            }
            cartFound = true;
        }else{
            System.out.println("Enter the quantity");
            quantity = scanner.nextInt();
            scanner.nextLine();
            // s.addItem(productName);
            if (s.addItem(productName,quantity)){
                System.out.println("Added "+quantity+" "+productName+" to cart: "+s.getName());
            }else{
                System.out.println("Product not found/ not enough quantity.");
            }
            cartFound = true;
        }
        if (cartFound == false){
            System.out.println("Cart not found.");
        }
    }

    public static void removeProductFromCart() throws Exception{
        Scanner scanner = new Scanner(System.in);
        String shoppingCartName;
        String productName;
        int quantity;
        boolean cartFound = false;

        System.out.println("Enter Shopping cart name: ");
        shoppingCartName = scanner.nextLine();
        ShoppingCart shoppingCart = ShoppingCartList.getShoppingCartObjectByName(shoppingCartName);

        System.out.println("Enter Product Name to remove from shopping cart");
        productName = scanner.nextLine();
        Product product = ProductList.getProductObjectByName(productName);

        if (product instanceof GiftableDigitalProduct | product instanceof GiftablePhysicalProduct){    
            System.out.println("Enter the quantity");
            quantity = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < quantity; i++){
                System.out.println("Enter the message you want to remove (by number)");
                int messRemove = scanner.nextInt();
                scanner.nextLine();
                while (messRemove>(giftMessageList.get(product).size()) && !(giftMessageList.get(product).size() == 0)){
                    System.out.println("Number is out of range. Please enter the message you want to remove (by number)");
                    messRemove = scanner.nextInt();
                    scanner.nextLine();
                }
                shoppingCart.removeItem(productName, 1);
                String tmpMessRemove = giftMessageList.get(product).get(messRemove-1);
                shoppingCart.removeGiftMessageForExistingProduct(productName, messRemove-1);
                System.out.println("Removed "+"1"+" "+productName + " with the gift message " + tmpMessRemove + " from cart: "+shoppingCart.getName());
                ShoppingCartList.viewAllShoppingCart();
            }
            cartFound = true;
        }else{
            System.out.println("Enter the quantity");
            quantity = scanner.nextInt();
            scanner.nextLine();
            if (shoppingCart.removeItem(productName,quantity)){
                System.out.println("Removed "+quantity+" "+productName+" from cart: "+shoppingCart.getName());
            }else{
                System.out.println("Product not found/ not enough quantity.");
            }
            cartFound = true;
        }

        if (cartFound == false){
            System.out.println("Cart not found.");
        }
    }
    
    
    public boolean addItem(String productName, int quantity) throws Exception{
        for (Product p: shoppingCart.keySet()){ //if the product existed in the shopping cart
            if (p.getName().equalsIgnoreCase(productName)){
                int currentQuantity = shoppingCart.get(p);
                shoppingCart.put(p, currentQuantity + quantity);
                p.setQuantityAvailable(p.getQuantityAvailable()-quantity);
                return true;
            }
        }
        for (Product product: ProductList.getProductList()){ //if the product not existed yet in the shopping cart
            if (product.getName().equals(productName) && product.getQuantityAvailable()>0 && (product.getQuantityAvailable()-quantity>0) && quantity > 0){
                shoppingCart.put(product, quantity);
                product.setQuantityAvailable(product.getQuantityAvailable()-quantity);
                return true;
            }
        }
        return false;
    }     
    
    public boolean addItem(String productName, int quantity, String giftMessage) throws Exception{
        for (Product p: shoppingCart.keySet()){ //if the product existed in the shopping cart
            if (p.getName().equalsIgnoreCase(productName)){
                int currentQuantity = shoppingCart.get(p);
                shoppingCart.put(p, currentQuantity + quantity);
                p.setQuantityAvailable(p.getQuantityAvailable()-quantity);
                addGiftMessageForExistingProduct(p, giftMessage);
                return true;
            }
        }
        for (Product product: ProductList.getProductList()){ //if the product not existed yet in the shopping cart
            if (product.getName().equals(productName) && product.getQuantityAvailable()>0 && (product.getQuantityAvailable()-quantity>0) && quantity > 0){
                shoppingCart.put(product, quantity);
                product.setQuantityAvailable(product.getQuantityAvailable()-quantity);
                addGiftMessageForNewProduct(product, giftMessage);
                return true;
            }
        }
        return false;
    }     

    public boolean removeItem(String productName, int quantity) throws Exception{
        for (Product p: shoppingCart.keySet()){
            if (!p.getName().equalsIgnoreCase(productName)){
                return false;
            }            
        }
        for (Product product: shoppingCart.keySet()){
            if (product.getName().equals(productName)){
                int currentQuantity = shoppingCart.get(product);
                if (currentQuantity - quantity <= 0){
                    shoppingCart.remove(product);
                    addQuantityForItemRemoval(productName, currentQuantity);
                    return true;
                }else{
                    shoppingCart.put(product, currentQuantity - quantity);
                    addQuantityForItemRemoval(productName, quantity);
                    return true;
                }        
            }
        }
        return false;
    }

    public boolean removeItem(String productName, int quantity, String giftMessage) throws Exception{
        for (Product p: shoppingCart.keySet()){
            if (!p.getName().equalsIgnoreCase(productName)){
                return false;
            }            
        }
        for (Product product: shoppingCart.keySet()){
            if (product.getName().equals(productName)){
                int currentQuantity = shoppingCart.get(product);
                if (currentQuantity - quantity <= 0){
                    shoppingCart.remove(product);
                    addQuantityForItemRemoval(productName, currentQuantity);
                    return true;
                }else{
                    shoppingCart.put(product, currentQuantity - quantity);
                    addQuantityForItemRemoval(productName, quantity);
                    return true;
                }        
            }
        }
        return false;
    }

    public boolean addQuantityForItemRemoval(String productName, int quantity) throws Exception{
        for (Product p: ProductList.getProductList()){
            if (p.getName().equals(productName)){
                p.setQuantityAvailable(p.getQuantityAvailable() + quantity);
                return true;
            }
        }
        return false;
    }

    public void addGiftMessageForNewProduct(Product product, String giftMessage) throws Exception{
        giftMessageList.put(product,new ArrayList<String>());
        giftMessageList.get(product).add(giftMessage);
    }      

    public void addGiftMessageForExistingProduct(Product product, String giftMessage) throws Exception{
        giftMessageList.get(product).add(giftMessage);
    }  

    public void removeGiftMessageForExistingProduct(Product product, int index){
        giftMessageList.get(product).remove(index);
    }

    public boolean removeGiftMessageForExistingProduct(String productName, int index){
        for (Product product: ProductList.getProductList()){
            if (product.getName().equalsIgnoreCase(productName)){
                giftMessageList.get(product).remove(index);
                return true;
            }
        }
        return false;
    }

    public double getPriceAterCoupon(Product p){
        if (this.couponApplied.getAppliedProductList().contains(p)){
            return ((p.getPrice()*(100-((PercentageCoupon) couponApplied).getCouponValue()))/100);
        }
        return p.getPrice();
    }

    /**
    Calculates the total cost of the products in the shopping cart.
    @return a double value of the total cost after coupon of the products in the shopping cart.
    */
    public double cartAmount(){
        double total = 0;
        if (this.couponApplied instanceof PriceCoupon){
            for (Product s: shoppingCart.keySet()){
                if(s instanceof DigitalProduct){
                    total += s.getPrice()*shoppingCart.get(s); //Price after coupon * quantity
                }
    
                else if(s instanceof GiftableDigitalProduct){
                    total += s.getPrice()*shoppingCart.get(s);
                }
    
                else if(s instanceof PhysicalProduct){
                    total += s.getPrice()*shoppingCart.get(s) +((PhysicalProduct) s).getWeight() *shoppingCart.get(s) * BASE_FEE;
                }
    
                else if(s instanceof GiftablePhysicalProduct){
                    total += s.getPrice()*shoppingCart.get(s) +((GiftablePhysicalProduct) s).getWeight()*shoppingCart.get(s) * BASE_FEE;
                }
            }       
            return (total - ((PriceCoupon) couponApplied).getCouponValue());

        }else if(this.couponApplied instanceof PercentageCoupon){
            for (Product s: shoppingCart.keySet()){
                if(s instanceof DigitalProduct){
                    total += this.getPriceAterCoupon(s)*shoppingCart.get(s); //Price after coupon * quantity
                }
    
                else if(s instanceof GiftableDigitalProduct){
                    total += this.getPriceAterCoupon(s)*shoppingCart.get(s);
                }
    
                else if(s instanceof PhysicalProduct){
                    total += this.getPriceAterCoupon(s)*shoppingCart.get(s) +((PhysicalProduct) s).getWeight() *shoppingCart.get(s) * BASE_FEE;
                }
    
                else if(s instanceof GiftablePhysicalProduct){
                    total += this.getPriceAterCoupon(s)*shoppingCart.get(s) +((GiftablePhysicalProduct) s).getWeight()*shoppingCart.get(s) * BASE_FEE;
                }
            }       
            return total;
        }
        for (Product s: shoppingCart.keySet()){
            if(s instanceof DigitalProduct){
                total += s.getPrice()*shoppingCart.get(s); //Price after coupon * quantity
            }

            else if(s instanceof GiftableDigitalProduct){
                total += s.getPrice()*shoppingCart.get(s);
            }

            else if(s instanceof PhysicalProduct){
                total += s.getPrice()*shoppingCart.get(s) +((PhysicalProduct) s).getWeight() *shoppingCart.get(s) * BASE_FEE;
            }

            else if(s instanceof GiftablePhysicalProduct){
                total += s.getPrice()*shoppingCart.get(s) +((GiftablePhysicalProduct) s).getWeight()*shoppingCart.get(s) * BASE_FEE;
            }
        }       
        return total;
    }

    /**
    Calculates the total weight of all the physical products in the shopping cart.
    @return a double value of the total weight of all the physical products in the shopping cart.
    */
    public double calculateWeight(){
        double totalWeight =0;
        for (Product s: shoppingCart.keySet()){
            for (Product p: ProductList.getProductList()){
                if(s.equals(p) && p instanceof PhysicalProduct){
                    totalWeight += ((PhysicalProduct) p).getWeight()*shoppingCart.get(s);
                }else if (s.equals(p) && p instanceof GiftablePhysicalProduct){
                    totalWeight += ((GiftablePhysicalProduct) p).getWeight()*shoppingCart.get(s);
                }
            }
        }
        return totalWeight;
    }
    
    public void addCoupon(Coupon coupon){
        this.couponApplied = coupon;
    }

    public void addCoupon(String couponName){
        Coupon coupon = CouponList.getCouponObjectByName(couponName);
        this.couponApplied = coupon;
    }

    public static void addCouponToCart(){
        Scanner scanner = new Scanner(System.in);
        String couponId;
        String shoppingCartName;
        boolean cartFound = false;

        System.out.println("Enter Shopping cart name: ");
        shoppingCartName = scanner.nextLine();

        for (ShoppingCart s: ShoppingCartList.getShoppingCartList()){
            if (shoppingCartName.equalsIgnoreCase(s.getName())){
                ShoppingCart sh = ShoppingCartList.getShoppingCartObjectByName(shoppingCartName);

                System.out.println("Enter Coupon ID to add to shopping cart");
                couponId = scanner.nextLine();
                sh.addCoupon(couponId);
                cartFound = true;
                System.out.println("Added coupon "+couponId+" to cart "+sh.getName());
            }
        }

        if (cartFound == false){
            System.out.println("Cart not found.");
        }
    }
    

    // Getters and Setters

    public HashMap getShoppingCart(){
        return shoppingCart;
    }

    public static double getBASE_FEE(){
        return BASE_FEE;
    }

    public void setShoppingCart(HashMap<Product, Integer> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Coupon getCouponApplied() {
        return this.couponApplied;
    }

    public void setCouponApplied(Coupon couponApplied) {
        this.couponApplied = couponApplied;
    }

    
}
