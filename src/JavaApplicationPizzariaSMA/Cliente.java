/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author jean
 */
public class Cliente extends Agent {
    protected void setup() {
        addBehaviour(new OneShotBehaviour(this) {
          public void action() {
              ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
              msg.addReceiver(new AID("telefonistaSara", AID.ISLOCALNAME));
              msg.setLanguage("Português");
              msg.setOntology("Pedido");
              msg.setContent("Pizza");
              myAgent.send(msg);
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
    }
}
