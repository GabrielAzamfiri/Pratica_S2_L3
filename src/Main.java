import com.github.javafaker.Faker;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {


        Supplier<Product> productSupplierBook = () -> {
            Faker f = new Faker(Locale.ITALY);
            return new Product(f.book().title(), "Books", new Random().nextDouble(1, 1000));
        };

        Supplier<Product> productSupplierBaby = () -> {
            Faker f = new Faker(Locale.ITALY);
            return new Product(f.pokemon().name(), "Baby", new Random().nextDouble(1, 1000));
        };
        Supplier<Product> productSupplierBoys = () -> {
            Faker f = new Faker(Locale.ITALY);
            return new Product(f.dragonBall().character(), "Boys", new Random().nextDouble(1, 1000));
        };

        Supplier<List<Product>> productSupplierList = () -> {
            List<Product> productList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                productList.add(productSupplierBook.get());
                productList.add(productSupplierBaby.get());
                productList.add(productSupplierBoys.get());

            }
            return productList;
        };
        List<Product> randomProducts = productSupplierList.get();
        randomProducts.forEach(System.out::println);

        System.out.println("****** Es_1 **********************************************************************************************");


        Stream<Product> booksCategory100Plus = randomProducts.stream().filter(product -> Objects.equals(product.getCategory(), "Books")).filter(product -> product.getPrice() > 100);
        //  booksCategory100Plus.forEach(book -> System.out.println("Nome libro con prezzo > 100:  " + book.getName() + " con prezzo: " + book.getPrice()));
        System.out.println(booksCategory100Plus.toList());


        System.out.println("****** Es_2 **********************************************************************************************");

        Customer DiegoCustomer = new Customer("Diego", 1);
        Customer EddyCustomer = new Customer("Eddy", 2);
        Customer AriannaCustomer = new Customer("Arianna", 3);
        Customer GabrielCustomer = new Customer("Gabriel", 2);


        Supplier<List<Product>> newProductSupplierList = () -> {
            List<Product> productList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                int random = new Random().nextInt(1, randomProducts.size());
                productList.add(randomProducts.get(random));
            }
            return productList;
        };


        Order orderDiego = new Order("In attesa", newProductSupplierList.get(), DiegoCustomer);
        Order orderEddy = new Order("In attesa", newProductSupplierList.get(), EddyCustomer);
        Order orderArianna = new Order("In attesa", newProductSupplierList.get(), AriannaCustomer);
        Order orderGabriel = new Order("In attesa", newProductSupplierList.get(), GabrielCustomer);

        List<Order> newOrders = new ArrayList<>();
        newOrders.add(orderDiego);
        newOrders.add(orderEddy);
        newOrders.add(orderArianna);
        newOrders.add(orderGabriel);

        List<Order> ordersBabyProducts = newOrders.stream().filter(order -> order.getProducts().stream().anyMatch(product -> "Baby".equals(product.getCategory()))).toList();

        ordersBabyProducts.forEach(System.out::println);


        System.out.println("****** Es_3 **********************************************************************************************");

        Stream<Product> boysCategory = randomProducts.stream().filter(product -> Objects.equals(product.getCategory(), "Boys"));
        List<Product> boysCategoryList = boysCategory.toList();
        System.out.println(boysCategoryList);
        boysCategoryList.stream().map(product -> "Sconto Boys 10% : il nuovo prezzo del prodotto " + product.getName() + " è: " + product.getPrice() / 100 * 10 + " $").forEach(System.out::println);

        System.out.println("****** Es_4 **********************************************************************************************");

        List<Order> ordersTier2 = newOrders.stream().filter(order -> order.getCustomer().getTier() == 2).toList();
        List<List<Product>> listaProdottiTier2 = new ArrayList<>();

        ordersTier2.forEach(order -> listaProdottiTier2.add(order.getProducts()));
        System.out.println(listaProdottiTier2);
    }
}