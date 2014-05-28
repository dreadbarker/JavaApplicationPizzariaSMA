/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaApplicationPizzariaSMA;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author jean
 */
public class Motoboy extends Agent {
    private DFAgentDescription dfd = new DFAgentDescription();
    private boolean registrado = false;
    
    public void SeRegistrarParaServicoTransporte()
    {   
        //vamos registrar o agente no DF
        try
        {
            //register(agente que oferece, descrição)
            DFService.register(this, dfd);
            this.registrado = true;
            System.out.println("motoboy "+ this.getLocalName()+" está registrado no DF.");
        } catch (FIPAException e) 
        {
            System.out.println(e.getMessage());
        }   
    }
    
    public void SeDesregistrarParaServicoTransporte(Agent myAgent)
    {
        try
        {
            //register(agente que oferece, descrição)
            DFService.deregister(myAgent);
            this.registrado = false;
            System.out.println("motoboy "+myAgent.getLocalName()+" não está mais registrado no DF.");
        } catch (FIPAException e) 
        {
            System.out.println(e.getMessage());
        } 
    }
    
    protected void setup() {
                
        //Criamos uma entrada no DF        
        dfd.setName(getAID()); //Informamos a AID do agente
        
        //vamos criar um serviço
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Transporte");
        sd.setName("Delivery");
        //adicionar o serviço
        dfd.addServices(sd);
        
        SeRegistrarParaServicoTransporte();
        
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = myAgent.receive();
                if(msg != null) {
                    String content = msg.getContent();
                    
                    //Verificar disponibilidade
                    if(registrado)
                    {
                        System.out.println("Pizzaiolo " + msg.getSender().getLocalName() + " pediu para entregrar a pizza do cliente "+ content +" e motoboy "+myAgent.getLocalName()+" irá.");
                                                
                        //responder e avisar ao pizzaiolo que está disponível                        
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                        reply.setContent("Vou entregrar a pizza do cliente " + content + " e já volto.");
                        myAgent.send(reply);
                                                                
                        SeDesregistrarParaServicoTransporte(myAgent);

                        //Se registrar novamente daqui a unidade de tempo determinada
                        TimerRegistrarParaServicoTransporte timer = new TimerRegistrarParaServicoTransporte(7, Motoboy.this);      
                        
                        //Enviar mensagem para cliente
                        ACLMessage mensagemParaCliente = new ACLMessage(ACLMessage.INFORM);
                        mensagemParaCliente.addReceiver(new AID(content, AID.ISLOCALNAME));
                        mensagemParaCliente.setLanguage("Português");
                        mensagemParaCliente.setOntology("Pedido");
                        mensagemParaCliente.setContent("Ó véio, aqui tá tua pizza. Valeu, pela preferência mano.");
                        myAgent.send(mensagemParaCliente);   
                    }
                    else
                    {
                        System.out.println("Pizzaiolo " + msg.getSender().getLocalName() + " pediu para entregar a pizza do cliente "+ content +" mas motoboy "+myAgent.getLocalName()+" está ocupado.");
                        
                        //responder e avisar ao pizzaiolo que não está disponível                        
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                        reply.setContent("Tô na corrida mano, agora não dá!");
                        myAgent.send(reply);                                   
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
