package dk.statsbiblioteket.util.rpc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RMISecurityManager;

/**
 *
 */
public class TestRemoteIFaceImpl extends UnicastRemoteObject
                                 implements TestRemoteIFace {

    String msg;
    Log log;

    public TestRemoteIFaceImpl(String msg) throws Exception {
        super (2768);

        log = LogFactory.getLog (TestRemoteIFaceImpl.class);
        this.msg = msg;

        Registry reg;
        try {
            reg = LocateRegistry.createRegistry(2767);
            log.info ("Created registry on port " + 2767);
        } catch (Exception e) {
            reg = LocateRegistry.getRegistry(2767);
            log.info ("Found registry on port " + 2767);
        }

        log.info ("Binding in registry with service on port " + 2768);
        reg.bind ("test", this);

        log.info ("Ready");
    }

    public String ping() {
        log.debug ("Got ping");
        return msg;
    }

    public static void main (String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        try {
            new TestRemoteIFaceImpl("standalone test");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
