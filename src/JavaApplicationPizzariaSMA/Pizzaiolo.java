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
                if(msg != null) 
                {                    
                    //se pizzaiolo receber mensagem de sara
                    if(msg.getSender().getLocalName().equals("telefonistaSara"))
                    {
                        //reponder telefonista
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("Recebi o pedido do cliente! Obrigado.");
                        myAgent.send(reply);
                        
                        System.out.println("A telefonista " + msg.getSender().getName() + " me avisou de um pedido, vou fazer a pizza");
                        System.out.println("Pizza do cliente " + msg.getContent() + " pronta!");
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
    
}
