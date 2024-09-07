# Banking Application

## Overview

This banking application, developed in Java, utilizes the State Design Pattern to manage account states effectively. It features a user-friendly UI for seamless interaction and supports two types of users: Customers and Managers.

## Features

### For Managers
- **Create Accounts**: Managers can create new accounts for customers.
- **Delete Accounts**: Managers can delete existing accounts.

### For Customers
- **Account Management**: Customers can manage their accounts, including viewing details and balance.
- **Deposit Funds**: Customers can deposit money into their accounts.
- **Online Purchases**: Customers can make online purchases.
- **Account Levels**: The account level is determined by the account balance, affecting purchase charges.

### Common Features
- **Login/Logout**: Both customers and managers can log in and log out of their accounts.
- **Balance Level Adjustment**: If a customer's balance changes levels, the amount charged for online purchases adjusts accordingly.

## Design Pattern

### State Design Pattern

The State Design Pattern is implemented in this application to handle different states of customer accounts. It allows:
- **State Transitions**: Managing transitions between different account states based on balance.
- **Encapsulation of State-Specific Behavior**: Implementing logic specific to each state, such as varying charges for purchases.
- **Simplified Code**: Avoiding complex conditional statements by encapsulating state-specific logic in separate classes.

## User Interface

The application features a graphical user interface (GUI) designed to enhance user experience:
- **Intuitive Layout**: Easy-to-navigate screens for both managers and customers.
- **Account Management**: Forms and controls for creating, managing, and deleting accounts.
- **Transaction Handling**: Input fields and buttons for depositing funds and making purchases.
- **Balance Display**: Visual representation of account balance and level changes.

## Getting Started

To get started with this project, follow these instructions:

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Integrated Development Environment (IDE) such as NetBeans or IntelliJ IDEA

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/geethavanr/banking-application.git
