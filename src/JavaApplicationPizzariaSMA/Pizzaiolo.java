/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author jean
 */
public class Pizzaiolo extends Agent {
    
    protected void setup() {
                
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = myAgent.receive();
                if(msg != null) {
                    String content = msg.getContent();
                    System.out.println(msg.getSender().getName() + "--> " + this.myAgent.getLocalName()+ ":" + content);
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
    
}
