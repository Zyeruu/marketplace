# Marketplace

A terminal-based marketplace simulation built in Java as a learning project. The system allows users to search for products, manage their cart and complete purchases. Users can also open a store and sell their own products.

## About

This project was developed with pure Java, no external frameworks, to practice and consolidate core Java concepts, including object-oriented programming, MVC architecture, SOLID principles, and dependency injection.

## Demo

```
--------| LOGIN | SIGN UP |--------
[1] Log in
[2] Sign up
[3] Exit
-----------------------------------
>> 2

------------| REGISTER |-----------
E-mail: example@domain.com
Password: Password
Your name: Your Name

[+] Account successfully created!

-----------| MAIN PAGE |-----------
[1] My Account
[2] My Purchases
[3] My Cart
[4] Search for products
[5] Search for sellers
[6] Log out
```

After opening a store, the main page expands with store management options:

```
-----------| MAIN PAGE |-----------
[1] My Account
[2] My Store
[3] My Purchases
[4] My Cart
[5] Search for products
[6] Search for sellers
[7] Log out
```

## Features

### User
- Sign up and log in
- View profile
- Change email and password
- Delete account
- Search products by name, type, name & type or view all
- Search sellers and view their catalogs
- View full details of a specific product and its reviews
- Add and remove products from cart
- View cart by name, type or view all
- Select and deselect cart products for checkout
- Checkout with payment method selection
- View purchase history and specific purchase details
- Review purchased products, edit and delete reviews
- View list of unreviewed purchases

### Store owner
- Open, update and delete store
- List, update, delete and toggle availability of products in catalog
- View catalog by name, type or view all
- View full details of a specific product
- View sales history and specific sale details

## Project Structure

```
src/
├── app/
├── checkout/
│   ├── controller/
│   ├── model/
│   ├── repository/
│   └── view/
├── config/
├── database/
├── exceptions/
├── user/
│   ├── account/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── repository/
│   │   └── view/
│   ├── auth/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── repository/
│   │   └── view/
│   ├── controller/
│   ├── dto/
│   ├── model/
│   ├── repository/
│   ├── search/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── repository/
│   │   └── view/
│   ├── store/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   └── view/
│   └── view/
└── shared/
    ├── enums/
    ├── model/
    ├── session/
    └── utils/
```

## Concepts Applied

- **MVC** with clear separation between Model, View and Controller layers
- **SOLID** with single responsibility, interface segregation and dependency inversion principles
- **Object-Oriented Programming** with encapsulation, polymorphism and custom exceptions
- **Dependency Injection** with dependencies instantiated and injected manually via `DependencyInjector`
- **Package by feature and by layer** organized by domain context with internal layer separation

## Requirements

- Java 17 or higher

## How to Run

### IDE
Open the project in any Java-compatible IDE and run `MarketplaceApplication.java`. Navigate using the numbered options displayed in the terminal.

### Terminal
Download the latest `.jar` file from [Releases](https://github.com/Zyeruu/marketplace/releases) and run:

```
java -jar marketplace.jar
```