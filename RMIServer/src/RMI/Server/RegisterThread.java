/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RMI.Server;

import Interface.ClientCallBack;

/**
 *
 * @author AdministradorSA
 */
public class RegisterThread extends Thread{
    ClientCallBack cb;
    String username;
    ServerImpl server;
    
    public RegisterThread(ClientCallBack cb, String username, ServerImpl server) {
        this.cb = cb;
        this.username = username;
        this.server = server;
    }

    @Override
    public void run() {
        server.registerClient2(cb, username);
    }
    
    
    
}
