/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import UI.BackgroundImagemJFrame;
import java.util.Timer;
/**
 *
 * @author jean
 */
public class TimerCriarCliente {
    
    Timer timer;
    public TimerCriarCliente(int segundos, jade.wrapper.AgentContainer ac, BackgroundImagemJFrame jframe)
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTaskCriarCliente(timer, ac, jframe), 0, segundos*1000);
    }
}
