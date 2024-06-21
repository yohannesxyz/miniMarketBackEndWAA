# MiniMarketBackEndWAA

## Overview
This project is an engineering proof of concept aimed at providing hands-on experience in building a full-stack application using Spring and React technologies. The project involves developing a Mini Online Market where different roles (Admin, Seller, and Buyer) interact with the system to manage products, orders, and reviews.

## Project Requirements

### Admin
- Approve seller registrations before they can post products.
- Delete reviews made by buyers if they violate guidelines.

### Seller
- Register as a seller.
- Manage Products (CRUD). Products that have already been purchased cannot be deleted.
- Sellers cannot buy products from the website.
- Maintain orders:
  - Cancel Order: The status of the order on the buyer’s part should also change.
  - Change Order status: Shipped, On the way, Delivered.
- View products with inventory. If a product is out of stock, it should be indicated.

### Buyer
- Register as a buyer.
- Buyers cannot sell items on this website.
- Place orders:
  - Maintain Shopping Cart (CRUD).
  - Maintain Shipping and Billing Address.
  - Maintain Payment (assuming a third-party handles payment processing).
  - Gain points for every successful purchase (not returned), which can be used like coupons.
- Maintain Orders:
  - Check Order History.
  - Cancel orders before shipping; cannot cancel after shipping.
  - Download/Print receipt as PDF or Excel.
- Write Product Reviews.

### General
- Login/Logout.
- Security with JWT (Users should not be able to access unauthorized pages) – Auth Server not required, can be maintained within the project.
- Process verifications (user gets an email of purchase, gets a message).
- Validation for all form submissions.
- Lazy loading of products, shown on different pages.

## Technical Aspects
- Neat code and organization.
- Managed packages, folders, and files.

## UI Requirements
- Each seller should have their own page or tab when selected.
- Buyers can filter products based on various filters.

## Project Submission
- List all Team Members with Student ID.
- List all features implemented in your project.
- Document how to configure/install the application.
- Application should have pre-populated data.
- Source code or GitHub links including documents or links for check-in history.
- Create a video showcasing the functionalities of your project.
- Only one project should be submitted per group.
- **Due Date:** June 20, 2024 (Thursday) 11:00 PM – could be extended to June 21 if required.

## Product-Specific Filters
- **Category/Subcategory:** Filters products based on predefined categories (e.g., Electronics, Clothing, Home Appliances).
- **Price Range:** Allows users to set a minimum and maximum price to filter products within their budget.
- **Brand:** Filters products by specific brands or manufacturers.
- **Ratings and Reviews:** Allows users to filter products based on average customer ratings or the number of reviews.
- **Discounts and Offers:** Filters products that are currently on sale or have special offers.
- **New Arrivals:** Filters products that are new to the store or recently added.
- **Best Sellers:** Filters products that are the most popular or frequently purchased.
- **Availability:** Filters products based on their stock status (e.g., In Stock, Out of Stock).
- **Product Type:** Specific to the type of product, such as size for clothing (S, M, L, XL), material type, etc.

## Technical and Functional Filters
- **Color:** Filters products by color options.
- **Size/Dimensions:** Filters products by size (for clothing) or dimensions (for furniture, electronics).
- **Material:** Filters products by the type of material used (e.g., Cotton, Leather, Plastic).
- **Features:** Filters products by specific features (e.g., waterproof, wireless, energy-efficient).
- **Compatibility:** Filters products based on compatibility with other items (e.g., electronics that work with specific operating systems).
- **Model/Year:** Filters products by their model number or the year of release.

## Contextual Filters
- **Location/Delivery Options:** Filters products based on delivery availability to specific locations.
- **Seller:** Filters products by specific sellers or stores within a marketplace.
- **Payment Options:** Filters products based on available payment methods (e.g., Credit Card, PayPal, Cash on Delivery).

## Additional Filters
- **Customer Demographics:** Filters based on target demographics like gender, age group, etc.
- **Usage:** Filters products based on intended use (e.g., casual wear, formal wear, outdoor gear).
- **Occasion:** Filters products for specific occasions (e.g., weddings, parties, holidays).

## Example Use Case
For a clothing e-commerce website:

- **Category:** Men, Women, Kids
- **Subcategory:** Shirts, Pants, Dresses, Jackets
- **Price Range:** $10 - $50, $50 - $100, $100 - $200
- **Brand:** Nike, Adidas, Zara, H&M
- **Size:** XS, S, M, L, XL
- **Color:** Red, Blue, Black, White
- **Material:** Cotton, Polyester, Wool
- **Discounts:** 10% off, 20% off, 50% off
- **Ratings:** 4 stars and above, 3 stars and above

These filters can be combined to create a more tailored and efficient shopping experience for users.
