package Model;

import Interface.ClientCallBack;
import Controlers.ChatMenuControler;
import Controlers.ISubscriber;
import Controlers.PublicChatControler;
import Interface.IServer;
import Model.MessageSenders.PrivateMessagesThread;
import Model.MessageSenders.PublicMessagesThread;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Facade {

    private ArrayList<ISubscriber> subscribers;
    private IServer server;
    private Registry reg;
    private String username;
    private String reciver;
    private static Facade instance;

    private Facade() {
        subscribers = new ArrayList<>();
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    //para ver si sirve
    public static Facade getInstance(ISubscriber subscriber) {
        if (instance == null) {
            instance = new Facade();
        }
        instance.subscribe(subscriber);
        return instance;
    }

    public void registerClient(ClientCallBack callBack, String username) throws RemoteException {
        try {
            setUsername(username); //El usuario solo va a tener un username, entonces lo guardamos en la fachada
            server.registerClient(callBack, username); //Manda a registrar el callback
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el Cliente");
            throw new RuntimeException(e);
        }
    }

    public void sendDirectMessage(String message) {
        PrivateMessagesThread thread = new PrivateMessagesThread(username, reciver, message, server);
        thread.start();
    }

    public void sendPublicMessage(String message) {
        PublicMessagesThread thread = new PublicMessagesThread(message, username, server);
        thread.start();
    }

    public void notifyPublicMessage(String from, String message) {
        for (ISubscriber subscriber : subscribers) {
            if (subscriber instanceof PublicChatControler) {
                ((PublicChatControler) subscriber).recivePublicMessage(from, message);
            }
        }
    }

    public void notifyPrivate(String message, String sender) {
        if (sender.equals(reciver)) {
            for (int i = 0; i < subscribers.size(); i++) {
                ISubscriber subscriber = subscribers.get(i);
                subscriber.reciveNotification(message);
            }
        }

    }

    public void subscribe(ISubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void updateConectedUsers(ArrayList<String> users) {
        users.remove(username);
        for (ISubscriber subscriber : subscribers) {
            if (subscriber instanceof ChatMenuControler) {
                ((ChatMenuControler) subscriber).updateConectedUsers(users);
            }
        }
    }

    //GETTERS SETTERS
    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public Registry getReg() {
        return reg;
    }

    public void setReg(Registry reg) {
        this.reg = reg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public IServer getServer() {
        return server;
    }

    public void setServer(IServer server) {
        this.server = server;
    }
}
