package RMI.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import Interface.IServer;
import Interface.ClientCallBack;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerImpl extends UnicastRemoteObject implements IServer {

    private final AtomicBoolean isActiveUsersThreadStarted = new AtomicBoolean(false);
    private final Map<String, ClientCallBack> clients = new ConcurrentHashMap<>();
    private final List<String> usersList = new CopyOnWriteArrayList<>();
    ActiveUsersThread activeUsersThread;

    public ServerImpl() throws RemoteException {
        super();  // Exporta el objeto
        //usersList = new ArrayList<>();
        activeUsersThread = new ActiveUsersThread(this);
    }

    /**
     * Método para registrar un cliente, es decir, añadirlo a la lista de
     * clientes que maneja el servidor para poder tener acceso futuro. este
     * método tambien añade el username a la lista de nombres para los usuarios
     * conectados y llama updateList()
     *
     * @param cb Objeto remoto que representa al cliente
     * @param username la clave para guardar el objeto remoto
     */
    public void registerClient(ClientCallBack cb, String username) {
        synchronized (this) {
            clients.put(username, cb);
            usersList.add(username);
        }

        // Si no hay hilo arrancado, arranca uno nuevo
        if (isActiveUsersThreadStarted.compareAndSet(false, true)) {
            // 1. Crea una instancia nueva del hilo
            activeUsersThread = new ActiveUsersThread(this);
            // 2. Arráncalo
            activeUsersThread.start();
        }

        try {
            updateList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void registerClient2(ClientCallBack cb, String username) {
        clients.put(username, cb);
        if (clients.size() == 1) {
            activeUsersThread.start();
        }
        //System.out.println("Cliente '" + username + "' registrado");
        usersList.add(username);
        try {
            updateList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envia mensaje directo (privado) de un usuario a otro
     *
     * @param from remitente
     * @param to destinatario
     * @param msg mensaje a enviar
     * @throws RemoteException
     */
    @Override
    public synchronized void sendDirectMessage(String from, String to, String msg) throws RemoteException {
        ClientCallBack target = clients.get(to);
        if (target != null) {
            try {
                target.receiveMessage(from, msg);
            } catch (RemoteException e) {
                clients.remove(to);
                System.out.println("Cliente '" + to + "' removido por desconexión");
            }
        } else {
            System.out.println("Usuario destino '" + to + "' no encontrado");
        }
    }

    @Override
    public synchronized void unrevisterClient(String username) throws RemoteException {
        // 1) Eliminar callback y nombre
        clients.remove(username);
        usersList.remove(username);

        // 2) Si ya no quedan clientes, detén el hilo de heartbeat
        if (clients.isEmpty()) {
            // Interrumpe el hilo para que salga de sleep o del loop
            activeUsersThread.interrupt();
            try {
                // Espera a que termine
                activeUsersThread.join();
            } catch (InterruptedException e) {
                // Restaurar estado de interrupción si lo necesitas
                Thread.currentThread().interrupt();
            }
            // 3) Resetea la bandera para poder crear un nuevo hilo luego
            isActiveUsersThreadStarted.set(false);
        }

        // 4) Notificar a los restantes (si hay) que la lista cambió
        updateList();
        System.out.println("Cliente '" + username + "' ha cerrado sesión");
    }

    /**
     * Le da la bienvenida al usuario cuando entra
     *
     * @param nombre nombre del usuario
     * @return retorna el saludo al usuario
     * @throws RemoteException
     */
    @Override
    public String darBienvenida(String nombre) throws RemoteException {
        String saludo = "Hola " + nombre + ". ¡Bienvenido al servidor!";
        System.out.println("Ejecutando darBienvenida para: " + nombre);
        return saludo;
    }

    @Override
    public void sendPublicMessage(String sender, String message) throws RemoteException {
        for (Map.Entry<String, ClientCallBack> entry : clients.entrySet()) {
            ClientCallBack callback = entry.getValue();
            if (entry.getKey().equals(sender)) {
                callback.recivePublicMessage("Tu", message);
                continue;
            }
            //System.out.println(entry.getKey());
            callback.recivePublicMessage(sender, message);
        }
    }

    //MÉTODOS
    /**
     * Manda a actualizar la lista de cada usuario por separado, enviandole la
     * lista de Strings con los nombres de cada uno
     */
    private void updateList() throws RemoteException {
        //System.out.println("lista de clientes:");
        for (Map.Entry<String, ClientCallBack> entry : clients.entrySet()) {
            ClientCallBack callback = entry.getValue();
            //System.out.println(entry.getKey());
            ArrayList<String> arrayList = new ArrayList<>(usersList);
            callback.reciveConectedUsers(arrayList);
        }
        System.out.println("Lista de Usuarios:");
        System.out.println(usersList);
        //System.out.println("-----------------------");
    }

    // Método en ServerImpl
    public void runList() throws RemoteException {
        for (Iterator<Map.Entry<String, ClientCallBack>> it = clients.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, ClientCallBack> entry = it.next();
            String user = entry.getKey();
            ClientCallBack cb = entry.getValue();
            try {
                cb.ping();  // Método dummy que lanza RemoteException si está caído
            } catch (RemoteException e) {
                it.remove();             // elimina del mapa
                usersList.remove(user);  // elimina de la lista de nombres
                updateList();            // notifica a los restantes
            }
        }
    }

}
