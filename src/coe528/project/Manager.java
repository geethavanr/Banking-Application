/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

public class Manager extends User {

    public Manager(String username, String password) {
        super(username, password, Role.MANAGER);
    }
}
