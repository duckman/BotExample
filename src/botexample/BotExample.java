/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botexample;

import botarena.util.Command;
import botarena.util.Debug;
import botarena.util.Direction;
import botarena.util.Packet;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author lucashereld
 */
public class BotExample implements Runnable
{
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private boolean running = false;

    public BotExample()
    {
        try
        {
            Socket socket = new Socket("localhost",1337);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch(Exception ex)
        {
            Debug.printex(ex);
        }
    }

    public void run()
    {
        try
        {
            ArrayList<String> auth = new ArrayList<String>();
            auth.add("test");
            auth.add("test");
            System.out.println("-- Authenticating");
            out.writeObject(new Packet(Command.AUTHENTICATE,auth));
            ArrayList<String> login = new ArrayList<String>();
            login.add("test1");
            System.out.println("-- Logging in");
            out.writeObject(new Packet(Command.LOGIN,login));
        }
        catch(Exception ex)
        {
            Debug.printex(ex);
        }

        System.out.println("-- Starting");
        running = true;
        while(running)
        {
            long start = System.currentTimeMillis();
            ArrayList<String> perams = new ArrayList<String>();

            perams.add(Direction.random().toString());

            try
            {
                out.writeObject(new Packet(Command.MOVE,perams));
                System.out.println("-- Packet sent");
                Thread.sleep(16 - (System.currentTimeMillis() - start));
            }
            catch(Exception ex)
            {
                Debug.printex(ex);
            }
        }
    }
}
