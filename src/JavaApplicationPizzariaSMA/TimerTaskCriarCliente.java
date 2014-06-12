/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import UI.BackgroundImagemJFrame;
import jade.wrapper.AgentContainer;
import java.awt.Graphics;
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
    private BackgroundImagemJFrame _jframe;
    
    public TimerTaskCriarCliente(Timer timer, jade.wrapper.AgentContainer ac, BackgroundImagemJFrame jframe)
    {
        this._timer = timer;
        this._ac = ac;
        this._jframe = jframe;
    }
    
    public void run() {
        //create new agents and pass it a reference to an object
        try
        {
//            Object referenceCliente = new Object();
//            Object argsCliente[] = new Object[1];
//            argsCliente[0] = referenceCliente;
//            jade.wrapper.AgentController cliente = this._ac.createNewAgent("cliente_"+this._count, "JavaApplicationPizzariaSMA.Cliente", argsCliente);
            
            JavaApplicationPizzariaSMA.Cliente cliente = new Cliente();
            cliente.setJFrame(_jframe);
            jade.wrapper.AgentController clienteAC = this._ac.acceptNewAgent("cliente_"+this._count, cliente);
            
            clienteAC.start();            
            
            this._jframe.AddClient(cliente.getName());
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
