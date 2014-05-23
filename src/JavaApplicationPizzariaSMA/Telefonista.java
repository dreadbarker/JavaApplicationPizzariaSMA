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
public class Telefonista extends Agent {
    
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = myAgent.receive();
                if(msg != null)
                {
                    ACLMessage reply = msg.createReply();
                    
                    String content = msg.getContent();
                    //com equalsIgnoreCase fazemos uma comparação
                    //não case-sensitive
                    if(content.equalsIgnoreCase("Pizza")) {
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("Recebi seu pedido! Obrigado.");
                        myAgent.send(reply);
                        
                       System.out.println("O agente " + msg.getSender().getName() + " avisou de um pedido.");
                       System.out.println("Vou avisar o pizzaiolo");
                    }
                } else 
                {
                    block();
                } //fim do action()
            } //fim do addbehaviour();
        });
    }
    
}
