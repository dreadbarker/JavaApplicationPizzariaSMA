/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import UI.BackgroundImagemJFrame;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.tools.sniffer.Sniffer;
import jade.wrapper.AgentContainer;

/**
 *
 * @author jean
 */
public class Main {

    /**
     * @param args the command line arguments
     */    
    private static AgentContainer cc;
    public static BackgroundImagemJFrame jframe;
    
    public static void main(String[] args) {
        // TODO code application logic here
        jframe = new BackgroundImagemJFrame();
        
        startJadePlatform();                
    }
        
    private static void startJadePlatform()
    {
        //http://wenku.baidu.com/view/aacb10eeaeaad1f346933f1d.html
        try{
            //get a hold on jade runtime
            jade.core.Runtime rt = jade.core.Runtime.instance();
            //create a default profile
            Profile p = new ProfileImpl();
            //create a new non-main container, connecting to the default
            //main container(i.e. on this host, port 1099            
            //ContainerController cc = rt.createAgentContainer(p);
            //cc = rt.createAgentContainer(p);
            cc = rt.createMainContainer(p);
                            
            //create new agents and pass it a reference to an object
//            Object reference = new Object();
//            Object args[] = new Object[1];
//            args[0] = reference;
//            jade.wrapper.AgentController telefonistaSara = cc.createNewAgent("telefonistaSara", "JavaApplicationPizzariaSMA.Telefonista", args);
            JavaApplicationPizzariaSMA.Telefonista telefonistaSara = new Telefonista();
            telefonistaSara.setJFrame(jframe);
            jade.wrapper.AgentController telefonistaSaraAC = cc.acceptNewAgent("telefonistaSara", telefonistaSara);
                        
//            //create new agents and pass it a reference to an object
//            Object referenceClienteJean = new Object();
//            Object argsClienteJean[] = new Object[1];
//            argsClienteJean[0] = referenceClienteJean;
//            jade.wrapper.AgentController clienteJean = cc.createNewAgent("clienteJean", "JavaApplicationPizzariaSMA.Cliente", argsClienteJean);
                                                 
            JavaApplicationPizzariaSMA.Motoboy motoboy = new Motoboy();
            motoboy.setJFrame(jframe);
            jade.wrapper.AgentController motoboyJoaoAC = cc.acceptNewAgent("motoboyJoao", motoboy);
            JavaApplicationPizzariaSMA.Motoboy motoboyPedro = new Motoboy();
            motoboyPedro.setJFrame(jframe);
            jade.wrapper.AgentController motoboyPedroAC = cc.acceptNewAgent("motoboyPedro", motoboyPedro);
            
            JavaApplicationPizzariaSMA.Pizzaiolo pizzaiolo = new Pizzaiolo();
            pizzaiolo.setJFrame(jframe);
            jade.wrapper.AgentController pizzaioloAC = cc.acceptNewAgent("pizzaioloBeto", pizzaiolo);
            
            //Adiciona os agentes na lista para serem espionados
            //http://jade.tilab.com/doc/tools/sniffer/html/jdoc/jade/tools/sniffer/Sniffer.html#sniffMsg%28jade.util.leap.List,%20boolean%29
            jade.tools.sniffer.Sniffer sniffer = new Sniffer();    
            jade.wrapper.AgentController snifferAC = cc.acceptNewAgent("sniffer", sniffer);
            snifferAC.start();
             
            //fire up agents
            pizzaioloAC.start();
            motoboyJoaoAC.start();
            motoboyPedroAC.start();
            telefonistaSaraAC.start();
            //criar cliente a cada x segundos
            TimerCriarCliente tcc = new TimerCriarCliente(30, cc, jframe);
             
        }catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            //Logger.getLogger(PlatformMediator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}
