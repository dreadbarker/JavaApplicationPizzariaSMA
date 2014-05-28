/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import jade.core.Agent;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author jean
 */
public class TimerTaskRegistrarParaServicoTransporte extends TimerTask 
{    
    private Timer _timer;
    private Motoboy _agent;
    
    public TimerTaskRegistrarParaServicoTransporte(Timer timer, Motoboy agent)
    {
        this._timer = timer;
        this._agent = agent;
    }
    
    public void run() {
        //create new agents and pass it a reference to an object
        try
        {
            this._agent.SeRegistrarParaServicoTransporte();
        }
        catch(Exception ex)
        {
            System.out.println("erro registrando motoboy no diretório facilitador:"+ex.getMessage());    
        }
        
        System.out.println("Motoboy voltou da entrega!");
        this._timer.cancel(); //Terminate the timer thread        
    }
}
