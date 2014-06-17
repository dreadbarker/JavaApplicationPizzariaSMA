/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jean
 */
public class BackgroundImagemJFrame extends JFrameComponentes//JFrame
{    
    private boolean _apresentacao;
    public void setApresentacao(boolean apresentacao)
    {
        this._apresentacao = apresentacao;
    }
    
    private int _tempoSpawnCliente;
    public void setTempoSpawnCliente(int tempoSpawnCliente) {
        this._tempoSpawnCliente = tempoSpawnCliente;
    }

    private int _tempoEntregaPizzaMotoboy;
    public void setTempoEntregaPizzaMotoboy(int tempoEntregaPizzaMotoboy) {
        this._tempoEntregaPizzaMotoboy = tempoEntregaPizzaMotoboy;
    }

    private int _tempoSleepEtapasApresentacao;
    public void setTempoSleepEtapasApresentacao(int tempoSleepEtapasApresentacao) {
        this._tempoSleepEtapasApresentacao = tempoSleepEtapasApresentacao;
    }
    
    private int _passo = 0;
    
    public BackgroundImagemJFrame()
    {       
        //Tamanho da janela
        int width = 1101;
        int height = 660;
        setTitle("Pizzaria Online simulação interface gráfica");
//        setSize(width,height);
//        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        //Adiciona imagem de fundo
        setLayout(new BorderLayout());
        //setContentPane(new JLabel(new ImageIcon("C:\\Users\\jean\\Dropbox\\Univali\\2014 - 1 semestre\\IA 2\\JavaApplicationPizzariaSMA\\src\\UI\\Image.png")));
        add(new JLabel(new ImageIcon("C:\\Users\\jean\\Dropbox\\Univali\\2014 - 1 semestre\\IA 2\\JavaApplicationPizzariaSMA\\src\\UI\\Image.png")));
//        setLayout(new FlowLayout());
//        GridLayout gl = new GridLayout(0, 5); 
//        setLayout(gl); 
//                
//        //Adiciona table cliente
//        JTable tabelaClientes = new JTable(1,1);
//        add(tabelaClientes);
//        
//        JTextField campoTeste = new JTextField("teste");
//        add(campoTeste);
        
        // Just for refresh :) Not optional!
        setSize(width,height);
        setSize(width+1,height+1);
    }
        
    public void AddClient(String nome)
    {
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();        
        model.addRow(new Object[]{nome});
    }
    
    public void RemoveClient(String nome)
    {
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        
        for(int i = 0; i<=0; i++)
        {
            String valorNome = (String)model.getValueAt(i, 0);
            if(nome.equals(valorNome))
            {
                model.removeRow(i);
                break;
            }
        }       
    }
    
    public void PassoDormir() {
        try {
            if(this._apresentacao)
            {
                jLabelPassoResposta.setText(String.valueOf(++_passo));
                Thread.sleep(_tempoSleepEtapasApresentacao * 1000);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
