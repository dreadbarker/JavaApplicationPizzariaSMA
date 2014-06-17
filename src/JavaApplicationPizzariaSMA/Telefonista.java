/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import UI.BackgroundImagemJFrame;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author jean
 */
public class Telefonista extends Agent {
    
    private BackgroundImagemJFrame _jframe;
    
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
                        String contentMsg = msg.getSender().getLocalName() + " Recebi seu pedido! Obrigado.";
                        reply.setContent(contentMsg);
                        myAgent.send(reply);                        
                        _jframe.jTextFieldTelefonistaRespostaCliente.setText(contentMsg);
                        _jframe.PassoDormir();
                        
                        System.out.println("O cliente " + msg.getSender().getName() + " avisou de um pedido, telefonista vai avisar o pizzaiolo");

                        //enviar mensagem para pizzaiolo
                        ACLMessage mensagemParaPizzaiolo = new ACLMessage(ACLMessage.INFORM);
                        mensagemParaPizzaiolo.addReceiver(new AID("pizzaioloBeto", AID.ISLOCALNAME));
                        mensagemParaPizzaiolo.setLanguage("Português");
                        mensagemParaPizzaiolo.setOntology("Pedido");
                        //no conteúdo, enviar nome do cliente
                        mensagemParaPizzaiolo.setContent(msg.getSender().getLocalName());
                        myAgent.send(mensagemParaPizzaiolo);                       
                        
                        _jframe.jTextFieldTelefonistaPizzaiolo.setText("Pedido do cliente "+ msg.getSender().getLocalName());
                        _jframe.PassoDormir();
                    }
                } else 
                {
                    block();
                } //fim do action()
            } //fim do addbehaviour();
        });
        
    }
    
    public void setJFrame(BackgroundImagemJFrame jframe)
    {
        this._jframe = jframe;
    }
}
