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
              msg.addReceiver(new AID("Telefonista", AID.ISLOCALNAME));
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
                    String content = msg.getContent();
                    System.out.println("--> "+msg.getSender().getName() + ":" + content);
                }
                else
                {
                    //com o block() bloqueamos o comportamento até qe uma nova mensagem
                    //chegue ao agente e assim evitamos consumir ciclos da cpu.
                    block();
                }
            }
        });
    }
}
