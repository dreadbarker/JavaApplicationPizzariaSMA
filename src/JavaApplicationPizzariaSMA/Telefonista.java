/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import jade.core.AID;
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
                    String content = msg.getContent();
                    //com equalsIgnoreCase fazemos uma comparação não case-sensitive
                    if(content.equalsIgnoreCase("Pizza")) {
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("Recebi seu pedido! Obrigado.");
                        myAgent.send(reply);
                        
                        System.out.println("O cliente " + msg.getSender().getName() + " avisou de um pedido, vou avisar o pizzaiolo");

                        //enviar mensagem para pizzaiolo
                        ACLMessage mensagemParaPizzaiolo = new ACLMessage(ACLMessage.INFORM);
                        mensagemParaPizzaiolo.addReceiver(new AID("pizzaioloBeto", AID.ISLOCALNAME));
                        mensagemParaPizzaiolo.setLanguage("Português");
                        mensagemParaPizzaiolo.setOntology("Pedido");
                        //no conteúdo, enviar nome do cliente
                        mensagemParaPizzaiolo.setContent(msg.getSender().getLocalName());
                        myAgent.send(mensagemParaPizzaiolo);                       
                    }
                } else 
                {
                    block();
                } //fim do action()
            } //fim do addbehaviour();
        });
        
    }
    
}
