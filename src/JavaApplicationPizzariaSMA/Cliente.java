/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import UI.BackgroundImagemJFrame;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.awt.Font;
import javax.swing.JFrame;

/**
 *
 * @author jean
 */
public class Cliente extends Agent {
    
    private BackgroundImagemJFrame _jframe;
    
    protected void setup() {
        addBehaviour(new OneShotBehaviour(this) {
          public void action() {
              ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
              msg.addReceiver(new AID("telefonistaSara", AID.ISLOCALNAME));
              msg.setLanguage("Português");
              msg.setOntology("Pedido");
              String content = "O cliente " + getAID().getName() + " está pedindo uma pizza.";
              System.out.println(content);
              msg.setContent("Pizza");
              myAgent.send(msg);
              
              _jframe.jTextFieldClienteTelefonista.setText(content);    
              _jframe.Dormir(5);
          }  
        });
        
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = myAgent.receive();
                if(msg != null) {
                    if(msg.getSender().getName().contains("telefonista"))
                    {
                        String content = msg.getContent();
                        System.out.println(msg.getSender().getName() + "--> " + this.myAgent.getLocalName()+ ":" + content);        
                    }
                    else if(msg.getSender().getName().contains("motoboy"))
                    {
                        String content = msg.getContent();
                        System.out.println(msg.getSender().getName() + "--> " + this.myAgent.getLocalName()+ ":" + content);
                        
                        String contentMsg = this.myAgent.getAID().getLocalName() + "diz: Obrigado.";
                        if(msg.getSender().getName().contains("motoboyPedro"))
                        {
                            _jframe.jTextFieldConversaMotoboyPedroCliente.setText(contentMsg);
                            _jframe.Dormir(5);
                        }            
                        else
                        {
                            _jframe.jTextFieldConversaMotoboyJoaoCliente.setText(contentMsg);
                            _jframe.Dormir(5);
                        }
                        
                        //invoca a execução do método takeDown()
                        doDelete(); 
                    }
                }
                else
                {
                    //com o block() bloqueamos o comportamento até que uma nova mensagem
                    //chegue ao agente e assim evitamos consumir ciclos da cpu.
                    block();
                }
            }
        });
    }
    
    protected void takeDown() {
        System.out.println("Agente "+getAID().getName()+" está finalizado.");
        
        this._jframe.RemoveClient(this.getName());
    }
    
    public void setJFrame(BackgroundImagemJFrame jframe)
    {
        this._jframe = jframe;
    }
}
