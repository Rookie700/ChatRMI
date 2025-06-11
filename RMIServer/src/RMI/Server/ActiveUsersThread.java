package RMI.Server;

import static java.lang.Thread.sleep;
import java.rmi.RemoteException;

public class ActiveUsersThread extends Thread {

    private final ServerImpl server;

    ActiveUsersThread(ServerImpl server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            // Repite mientras no estemos interrumpidos
            while (!Thread.currentThread().isInterrupted()) {
                server.runList();
                Thread.sleep(2000);
            }
        } catch (InterruptedException ie) {
            // Al interrumpir, salimos del run() limpiamente
            // (puedes loguear algo aquí si quieres)
        } catch (RemoteException re) {
            re.printStackTrace();
        }
        System.out.println("ActiveUsersThread terminado.");
    }
}
