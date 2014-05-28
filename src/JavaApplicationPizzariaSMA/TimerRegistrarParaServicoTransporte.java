/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import jade.core.Agent;
import java.util.Timer;

/**
 *
 * @author jean
 */
public class TimerRegistrarParaServicoTransporte {
    
    Timer timer;
    public TimerRegistrarParaServicoTransporte(int segundos, Motoboy agent)
    {
        timer = new Timer();
        timer.schedule(new TimerTaskRegistrarParaServicoTransporte(timer, agent), segundos*1000);
    }
}
