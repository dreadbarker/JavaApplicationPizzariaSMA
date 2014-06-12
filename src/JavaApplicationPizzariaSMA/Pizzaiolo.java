/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import UI.BackgroundImagemJFrame;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author jean
 */
public class Pizzaiolo extends Agent {
    
    private BackgroundImagemJFrame _jframe;
    
    private Queue<String> _clientesComPizzaPronta  = new LinkedList<String>();
    public Queue<String> getClientesComPizzaPronta()
    {
        return _clientesComPizzaPronta;
    }
        
    protected void setup() {
                        
        int segundo = 5;
        //comportamento para solicitar ao diretório falicitador por algum motoboy disponível para entregar a pizza
        addBehaviour(new TickerBehaviour(this, segundo*1000)
        {
            protected void onTick() 
            {
                //poll remove, por isso está sendo espiado
                String clienteLocalName = _clientesComPizzaPronta.peek(); 
                //retorna null se vazio
                if(clienteLocalName != null)
                {
                    System.out.println("Pizzaiolo vai verificar se algum motoboy poderia levar a pizza do cliente " + clienteLocalName);
                    _jframe.jTextFieldConversaPizzaioloMotoboys.setText("Buscando motoboy para entregar pizza do cliente "+clienteLocalName);
                    _jframe.Dormir(5);
                                        
                    //busca por quem fornece o serviço, passando o nome do cliente 
                    ServiceDescription servicoDelivery = new ServiceDescription();
                    servicoDelivery.setType("Transporte");

                    DFAgentDescription dfd = new DFAgentDescription();
                    dfd.addServices(servicoDelivery);
                    try
                    {
                        DFAgentDescription[] resultado = DFService.search(myAgent, dfd);
                        if (resultado.length != 0) 
                        {
                            //envia pedido para motoboy disponível que presta o serviço de transporte
                            ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
                            msg.addReceiver(resultado[0].getName());
                            msg.setContent(clienteLocalName); //nome do cliente
                            myAgent.send(msg);

                            _jframe.jTextFieldConversaPizzaioloMotoboys.setText(resultado[0].getName() + "Podes levar?");
                            _jframe.Dormir(5);
                            //Finaliza comportamento
//                            stop();
                        }
                    } catch(FIPAException e)
                    {
                        System.out.println(e.getMessage());                    
                    }
                }
            }
        });
        
        //comportamneto para receber mensagens 
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = myAgent.receive();
                if(msg != null) 
                {                    
                    //se pizzaiolo receber mensagem da telefonista sara vai fazer a pizza
                    if(msg.getSender().getLocalName().equals("telefonistaSara"))
                    {
                        //reponder telefonista
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        String contentMsg = "Recebi o pedido do cliente "+msg.getContent()+"! Obrigado.";
                        reply.setContent(contentMsg);                                                
                        myAgent.send(reply);
                        _jframe.jTextFieldPizzaioloRespondeTelefonista.setText(contentMsg);
                        _jframe.Dormir(5);
                        
                        System.out.println("A telefonista " + msg.getSender().getName() + " avisou pizzaiolo de um pedido, ele vai fazer a pizza");
                        System.out.println("Pizza do cliente " + msg.getContent() + " pronta!");
                        
                        _clientesComPizzaPronta.add(msg.getContent());
                    } 
                    else if(msg.getSender().getLocalName().contains("motoboy"))
                    {
                        //String content = msg.getContent();                        
                        if(msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL)
                        {
                            _clientesComPizzaPronta.poll();
                        }
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
    
    public void setJFrame(BackgroundImagemJFrame jframe)
    {
        this._jframe = jframe;
    }
}
