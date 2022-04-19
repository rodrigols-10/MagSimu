/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Execucao;

import Classes.Resultados;
import java.awt.Color;
import java.awt.Dimension;
import static java.awt.Frame.NORMAL;
import java.awt.Graphics;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import javax.swing.JPanel;

/**
 *
 * @author Rodrigo
 */
public class JRede extends JPanel {
    public int TamX;
    public int TamY;
    public int espaco;
    private int a; //colunas
    private int b;//linhas
    public int c;
    public int d;
    
    float k; //Valor que define o tamanho do VETOR na tela;
    float tamSeta; //Valor que define o tamanho da SETA na tela;
    
    double [][][]  Sx;// = new double[cm.getNX()][cm.getNY()];
    double [][][]  Sy;// = new double[cm.getNX()][cm.getNY()];
    
    public JRede(){
        TamX = 1010; // TÔ DOIDO PRA SABER COMO CONSEGUIR A RESOLUÇÃO DA TELA
        TamY =680;   // NÃO DESISTA!
        espaco = 20;
        k = 20;      // JÁ ISTO AQUI VAI PRECISAR DE UMA TAXA DE DIMINUIÇÃO PARA ESTRUTURAS MAIORES DO QUE A TELA PERMITE
        tamSeta = 10;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        if(Principal.isDesenharTela()){
            g.setColor(Color.white);
            Resultados res = Principal.getRes();
            Sx = res.getSX();
            Sy = res.getSY();
            
            if((Principal.getTG() == 1)||(Principal.getTG() == 3)){
                grade(g, res);    
                for(int i = 0; i < a;i++){
                    for(int j = 0; j < b;j++){
                        Vetores(g,i,j,Sx[i][j][0],Sy[i][j][0]);
                    }
                }
            } else { //não dá certo com esfera
                elipse(g,res);
                for(int i = 0; i < a;i++){
                    for(int j = 0; j < b;j++){
                        if(res.getEXISTE(i,b-1-j) == 1){
                            Vetores(g,i,j,Sx[i][b-1-j][0],Sy[i][b-1-j][0]);
                        }
                    }
                }
            }
        }else{
            g.setColor(Color.white);
        }
    }
    
    public void grade(Graphics g, Resultados res){
        TamX = res.getTamPanelX();
        TamY = res.getTamPanelY();
        a = res.getNX();
        b = res.getNY();
        espaco = 20;
        k = 20;
        tamSeta = 10;
        
        if(a*espaco>=TamX){ //Diminui o tamanho da grade (quando for grande demais) para caber na tela
            k = k * TamX/(a*espaco);
            tamSeta = tamSeta * TamX/(a*espaco);
            espaco = (int) espaco * TamX/(a*espaco);
        }
        if(b*espaco>=TamY){
            k = k * TamY/(b*espaco);
            tamSeta = tamSeta * TamY/(b*espaco);
            espaco = (int) espaco * TamY/(b*espaco);
        }
        
        c = (TamX - espaco*(getA()-1))/2; //Distancia horizontal da borda esquerda ate a grade
        d = (TamY - espaco*(getB()-1))/2; //Distancia Vertical da borda superior ate a grade
        
        g.setColor(Color.black);
        
        for (int i = 0; i < getA(); i++) {
            for (int j = 0; j < getB(); j++) {
                int coordx[] = {c+espaco*i-2, c+espaco*i-2, c+espaco*i+2, c+espaco*i+2 };
                int coordy[] = {d+espaco*j-2, d+espaco*j+2, d+espaco*j+2, d+espaco*j-2 };

                g.fillPolygon( coordx, coordy, 4);
            }
        }
        
    }
    
    public void Vetores(Graphics g, int i, int j,double Sx, double Sy){        
        g.setColor(Color.red);
        
        c = (TamX - espaco*(a-1))/2;
        d = (TamY - espaco*(b-1))/2;
        
        g.drawLine(c+espaco*i, d+espaco*(b-1-j), (int) (c+espaco*i + k*Sx), (int)(d+espaco*(b-1-j) - k*Sy));
        seta(g, k*Sx, k*Sy,i,j);
    }
    
    public void seta(Graphics g, double ca, double co, int i, int j){
        double x1=0;
        double y1=0;
        double x2=0;
        double y2=0;
        double ALPHA;
        double BETA;
        
        ALPHA = Math.asin(co/Math.sqrt(ca*ca + co*co)) - 23*Math.PI/180;
        BETA = 67*Math.PI/180 - Math.asin(co/Math.sqrt(ca*ca + co*co));
        
        x1 = tamSeta * Math.cos(ALPHA);
        y1 = tamSeta * Math.sin(ALPHA);
        x2 = tamSeta * Math.sin(BETA);
        y2 = tamSeta * Math.cos(BETA);
        
        if(ca<0){x1 *= -1; x2 *= -1;}//Isso resolve o problema das pontas das setas que saem do lugar quando 'ca<0'
        
        int coordx[] = {(int) (c+espaco*i + ca), (int) (c+espaco*i + ca - x1), (int) (c+espaco*i + ca - x2) };
        int coordy[] = {(int) (d+espaco*(b-1-j) - co), (int)(d+espaco*(b-1-j) - co + y1), (int)(d+espaco*(b-1-j) - co + y2) };
        
        g.fillPolygon( coordx, coordy, 3); // contorno poligonal fechado definido pelos 3 pontos 
        
    }
    
    public void elipse(Graphics g, Resultados res){
        TamX = res.getTamPanelX();
        TamY = res.getTamPanelY();
        a = res.getNX();
        b = res.getNY();
        espaco = 20;
        k = 20;
        tamSeta = 10;
        
        if(a*espaco>=TamX){ //Diminui o tamanho da grade (quando for grande demais) para caber na tela
            k = k * TamX/(a*espaco);
            tamSeta = tamSeta * TamX/(a*espaco);
            espaco = (int) espaco * TamX/(a*espaco);
        }
        if(b*espaco>=TamY){
            k = k * TamY/(b*espaco);
            tamSeta = tamSeta * TamY/(b*espaco);
            espaco = (int) espaco * TamY/(b*espaco);
        }
        //System.out.println(espaco+"\n"+k+"\n"+tamSeta+"\n");
        c = (TamX - espaco*(getA()-1))/2; //Distancia horizontal da borda esquerda ate a grade
        d = (TamY - espaco*(getB()-1))/2; //Distancia Vertical da borda superior ate a grade
        
        /*
        g.setColor(Color.gray);
        for (int i = 0; i < getA(); i++) { //colunas
            g.drawLine(c+espaco*i, d, c+espaco*i, d+espaco*(getB()-1));
        }
        
        for (int i = 0; i < getB(); i++) { //linhas
            g.drawLine(c, d+espaco*i, c+espaco*(getA()-1), d+espaco*i);
        }
        */
        /* Posso tirar essa parte para deixar o processo mais rápido, mas com isso aqui fica mais bonito */
        g.setColor(Color.black);
        for (int i = 0; i < getA(); i++) {
            for (int j = 0; j < getB(); j++) {
                if(res.getEXISTE(i,j) == 1){
                    int coordx[] = {c+espaco*i-2, c+espaco*i-2, c+espaco*i+2, c+espaco*i+2 };
                    int coordy[] = {d+espaco*j-2, d+espaco*j+2, d+espaco*j+2, d+espaco*j-2 };

                    g.fillPolygon( coordx, coordy, 4);
                }
            }
        }
        
    }
    
    public int getA() {
        return a;
    }
    
    public void setA(int a) {
        this.a = a;
    }
    
    public int getB() {
        return b;
    }
    
    public void setB(int b) {
        this.b = b;
    }
}
