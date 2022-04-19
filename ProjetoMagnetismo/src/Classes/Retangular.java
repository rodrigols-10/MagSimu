/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Execucao.Principal;

/**
 *
 * @author rodri
 */
public class Retangular extends CampoMagnetico {
    
    public Retangular(int X, int Y, int Z){
        super(X, Y, Z);
    }
    
    public void Inicializacao(String material, int nh, int nc, double hii, double hff, double dd, String an){
        FM      = material;
        HI      = hii;
        HF      = hff;
        NH      = nh;
        NCHECK  = nc;
        DELTETA = 0.100000000;
        TOLTETA = 0.000000001;
        PHIH    = 90;
        TETAH   = 0.000000000;
        D = dd;
        ANISP1 = an;
        
        if("CO".equals(FM)){
            EXCH1= 214.3/Math.pow(getD(), 2);
            ALFA1 = 0.9;
            ALFAD1 = 1.4;
        }
        else if("FE".equals(FM)){
            EXCH1= 147./Math.pow(getD(), 2);
            ALFA1 = 0.55;
            ALFAD1 = 1.7;
        }
        else if("PY".equals(FM)){
            EXCH1= 125./Math.pow(getD(), 2);
            ALFA1 = 0.00;
            ALFAD1 = 0.8000;
        }
        else if("NI".equals(FM)){
            EXCH1= 688.2/Math.pow(getD(), 2);
            ALFA1 = 0.202;
            ALFAD1 = 0.494;
        }
        //CALCULOS INICIAIS
        
        for(int k=0;k<NZ;k++){
             EXCH[k]=EXCH1;
             EXCHP[k]=EXCH1;
             EXCHM[k]=EXCH1;
             ALFAD[k]=ALFAD1;
        }
        EXCHM[0]=0;
        EXCHP[NZ-1]=0;
        
        ANISX = ALFA1;
        if("YES".equals(getANISP1())){
            ANISX = 10*ALFA1;
        }
        
        TETAH = TETAH * Math.PI/180;
        PHIH = PHIH * Math.PI/180;
        
        DELH = (HF-HI)/(NH-1);
        HEX = HI - DELH;
        
        for(int ix=0; ix<NX; ix++){
           for(int iy=0; iy<NY; iy++){
                for(int iz=0; iz<NZ; iz++){
                    SX0[ix][iy][iz] = 1 - DELTETA*DELTETA;
                    SY0[ix][iy][iz] = DELTETA;
                    SZ0[ix][iy][iz] = DELTETA;
                    
                    //SE A ANISOTROPIA ESTIVESSE EM OUTRA DIREÇÃO, ISTO MUDARIA
                }
           }
        }
        
        NTOT = NX * NY;
        
        texto = "";
        DADOS_GRAFICO = "";
    }
    
    public void Campo(Principal rede, Resultados res, int ih){
        //calculos do campo (HEX)
        double AJ; //(inicio)Dados do campo dipolar
        double AI;
        double SOMAX;
        double SOMAY;
        double AUX;
        double R3;
        double R5; //(  fim  )Dados do campo dipolar
           
        HEX = HEX + DELH;
        SXX = 0;
        SYY = 0;
        
        for(int ix=0; ix<NX; ix++){
            for(int iy=0; iy<NY; iy++){
                SX[ix][iy][0] = SX0[ix][iy][0]+DELTETA;
                SY[ix][iy][0] = SY0[ix][iy][0]-DELTETA;
            }
        }
        
        HEXX = HEX * Math.cos(TETAH)*Math.sin(PHIH);
        HEXY = HEX * Math.sin(TETAH)*Math.sin(PHIH);
        
        int ICONT = 0;
        
        do{
            for(int ix=0; ix<NX; ix++){
                for(int iy=0; iy<NY; iy++){
                    SX0[ix][iy][0] = SX[ix][iy][0];
                    SY0[ix][iy][0] = SY[ix][iy][0];
                }
            }
            
            double S1,S2,S3,S4;
            for(int ix=0; ix<NX; ix++){
                for(int iy=0; iy<NY; iy++){
                    //CONDIÇÕES PARA EVITAR PROBLEMAS NO CALCULO
                    if(ix == 0){     S1 = 0;  }else{  S1 = SX[ix-1][iy][0]; }
                    if(ix == NX-1){  S2 = 0;  }else{  S2 = SX[ix+1][iy][0]; }
                    if(iy == 0){     S3 = 0;  }else{  S3 = SX[ix][iy-1][0]; }
                    if(iy == NY-1){  S4 = 0;  }else{  S4 = SX[ix][iy+1][0]; }

                    HX = 0.25*EXCH1*(S1 + S2 + S3 + S4) + HEXX + ANISX*SX[ix][iy][0];
                    //0.25 (1/4) é a separação da energia para 4 vizinhos. em 3D será diferente
                    if(ix == 0){     S1 = 0;  }else{  S1 = SY[ix-1][iy][0]; }
                    if(ix == NX-1){  S2 = 0;  }else{  S2 = SY[ix+1][iy][0]; }
                    if(iy == 0){     S3 = 0;  }else{  S3 = SY[ix][iy-1][0]; }
                    if(iy == NY-1){  S4 = 0;  }else{  S4 = SY[ix][iy+1][0]; }

                    HY = 0.25*EXCH1*(S1 + S2 + S3 + S4) + HEXY;
                    
                    
                    AJ = 0;
                    AI = 0;
                    SOMAX = 0;
                    SOMAY = 0;                    
                    R3 = 0;
                    R5 = 0;

                    for (int i = 0; i < NX; i++) {
                        for (int j = 0; j < NY; j++) {
                            AUX = Math.sqrt(Math.pow(Math.abs(ix-i),2) + Math.pow(Math.abs(iy-j), 2));

                            if(AUX != 0){
                                R3 = 1/Math.pow(AUX, 3);
                                R5 = 1/Math.pow(AUX, 5);
                            }

                            SOMAX = 3*(SX[i][j][0]*(ix-i) + SY[i][j][0]*(iy-j))*(ix-i)*R5 - SX[i][j][0]*R3;
                            SOMAY = 3*(SX[i][j][0]*(ix-i) + SY[i][j][0]*(iy-j))*(iy-j)*R5 - SY[i][j][0]*R3;


                            AI = AI + SOMAX*ALFAD1;
                            AJ = AJ + SOMAY*ALFAD1;
                        }
                    }
                    
                    HX = HX + AI; //LOCAL FIELD AT EACH SPIN
                    HY = HY + AJ;
                    
                    HP = Math.sqrt(HX*HX + HY*HY);

                    SX[ix][iy][0] = HX/HP;
                    SY[ix][iy][0] = HY/HP;
                    HTX[ix][iy][0] = HX;
                    HTY[ix][iy][0] = HY;                    
                }
            }
            
            for(int ix=0; ix<NX; ix++){
                for(int iy=0; iy<NY; iy++){
                    TALZ =Math.abs(SX0[ix][iy][0]*HTY[ix][iy][0] - SY0[ix][iy][0]*HTX[ix][iy][0]);
                    
                    if(TALZ > TOLTETA){
                        break; //sai deste FOR imediatamente
                    }
                }
                if(TALZ > TOLTETA){
                        break; //se a mesma condição ocorrer, sai deste outro FOR também
                }
            }
            ICONT = ICONT + 1;
             
        }while((ICONT <= getNCHECK()) && (TALZ>TOLTETA));//FIM DO LOOP DO CAMPO
        
        for(int ix=0; ix<NX; ix++){
            for(int iy=0; iy<NY; iy++){
                SXX = SXX + SX[ix][iy][0];
                SYY = SYY + SY[ix][iy][0];
                
                //ANGULO DE CADA CAMPO (RAD)
                TETA[ix][iy][0] = Math.atan2(SY[ix][iy][0], SX[ix][iy][0]);
            }
        }
        
        SXX = SXX/NTOT;
        SYY = SYY/NTOT;
        
        //GRAVANDO RESULTADOS RELEVANTES
        res.setSX(SX);
        res.setSY(SY);
        res.setSXX(SXX);
        res.setSXX(SXX);
        res.setTETA(TETA);
        
        //DADOS DO GRAFICO
        DADOS_GRAFICO += HEX+" "+SXX+" "+SYY+"\r\n";
        //--------------------------------------------------------
        
        texto = "_______________________________________________\n";
        texto += "IH: "+ih+"/"+getNH()+"; HEX= "+HEX+";\nSXX="+SXX+"; SYY="+SYY+";\nTESTES: "+ICONT+"/"+getNCHECK()+"\n\n";
        /**
        if(ih == getNH()){ //Isso faz essa parte abaixo ser mostrada somente para o ultimo IH
            for (int i=0; i<NX;i++){
                for (int j=0; j<NY;j++){
                    texto += "Posicao: "+(i+1)+","+(j+1)+"; ";
                    texto += "\nSX= "+SX[i][j][0]+"; ";
                    texto += "SY= "+SY[i][j][0]+"; ";
                    texto += "\nTeta= "+TETA[i][j][0]*180/Math.PI+"°\n\n"; // MOSTRANDO EM GRAUS

                }
            }
        }
        */
        
        res.setDADOS_GRAFICO(DADOS_GRAFICO);
        
        texto = rede.Area().getText()+texto;
        rede.Area().setText(texto);
    }
    
    public void CampoDipolar(Resultados res,int ih){
        double AJ;
        double AI;
        double SOMAX;
        double SOMAY;
        double AUX;
        double R3;
        double R5;
        
        DADOS_DIPOLAR = "";
        
        for (int io = 0; io < NX; io++) {
            for (int jo = 0; jo < NY; jo++) {
                AJ = 0;
                AI = 0;
                SOMAX = 0;
                SOMAY = 0;
                R3 = 0;
                R5 = 0;
                
                for (int i = 0; i < NX; i++) {
                    for (int j = 0; j < NY; j++) {
                        AUX = Math.sqrt(Math.pow(Math.abs(io-i),2) + Math.pow(Math.abs(jo-j), 2));
                        
                        if(AUX != 0){
                            R3 = 1/Math.pow(AUX, 3);
                            R5 = 1/Math.pow(AUX, 5);
                        
                            SOMAX = 3*(SX[i][j][0]*(io-i) + SY[i][j][0]*(jo-j))*(io-i)*R5 - SX[i][j][0]*R3;
                            SOMAY = 3*(SX[i][j][0]*(io-i) + SY[i][j][0]*(jo-j))*(jo-j)*R5 - SY[i][j][0]*R3;

                            AI = AI + SOMAX*ALFAD1;
                            AJ = AJ + SOMAY*ALFAD1;
                        }
                    }
                }
                
                HDDX[io][jo][0] = AI;
                HDDY[io][jo][0] = AJ;
                
                HDD[io][jo][0] = Math.sqrt(Math.pow(HDDX[io][jo][0], 2) + Math.pow(HDDY[io][jo][0], 2));
                
                TETAHDD[io][jo][0] = Math.atan2(HDDY[io][jo][0], HDDX[io][jo][0]);
                
                //GRAVANDO RESULTADOS RELEVANTES
                res.setTETAHDD(TETAHDD);
                res.setHDDX(HDDX);
                res.setHDDY(HDDY);
                res.setHDD(HDD);
                
                DADOS_DIPOLAR += ih +" "+ io +" "+ jo +" "+ TETAHDD[io][jo][0] +" "+ HDD[io][jo][0] +" "+ TETAHDD[io][jo][0]*180/Math.PI +"\r\n";
            }
        }
        res.setDADOS_DIPOLAR(DADOS_DIPOLAR);
    }
}
