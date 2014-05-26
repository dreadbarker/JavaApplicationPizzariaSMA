/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import jade.wrapper.AgentContainer;
import java.util.TimerTask;
import java.util.Timer;

/**
 *
 * @author jean
 */
public class TimerTaskCriarCliente  extends TimerTask 
{
    private int _count=0;
    private Timer _timer;
    private jade.wrapper.AgentContainer _ac;
    
    public TimerTaskCriarCliente(Timer timer, jade.wrapper.AgentContainer ac)
    {
        this._timer = timer;
        this._ac = ac;
    }
    public void run() {
        //create new agents and pass it a reference to an object
        try
        {
            Object referenceCliente = new Object();
            Object argsCliente[] = new Object[1];
            argsCliente[0] = referenceCliente;
            jade.wrapper.AgentController cliente = this._ac.createNewAgent("cliente_"+this._count, "JavaApplicationPizzariaSMA.Cliente", argsCliente);
            cliente.start();
        }
        catch(Exception ex)
        {
            System.out.println("erro criando cliente:"+ex.getMessage());    
        }
        
        System.out.println("Cliente criado!");
        //timer.cancel(); //Terminate the timer thread
        this._count++;
    }
}
