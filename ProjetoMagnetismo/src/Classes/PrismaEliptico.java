/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Execucao.Principal;

/**
 *
 * @author User
 */
public class PrismaEliptico extends CampoMagnetico {
    //será uma combinação de PrismaRetangular e Eliptico
    public PrismaEliptico(int X, int Y, int Z){
        super(X, Y, Z);
    }
    public void Inicializacao(Resultados res, String material, int nh, int nc, double hii, double hff, double dd, String an){
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
            EXCH1=688.2/Math.pow(getD(), 2);
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
        
        
        
        //CONTANDO TOTAL DE CÉLULAS E CALCULANDO CÉLULAS EXISTENTES DA ELIPSE
        PontosElipse(NX, NY);
        res.setEXISTE(EXISTE);
        NTOT = 0;
        for(int ix=0; ix<NX; ix++){
            for(int iy=0; iy<NY; iy++){
                if(EXISTE[ix][iy] == 1){
                    NTOT += 1;
                }
            }
        }
        NTOT = NTOT * NZ;
        //FIM DA CONTAGEM
        
        texto = "";
        DADOS_GRAFICO = "";
    }
    public void CampoPrismaEliptico(Principal rede,Resultados res, int ih){
        //calculos do campo (HEX)
        double AK; //(inicio)Dados do campo dipolar
        double AJ;
        double AI;
        double SOMAX;
        double SOMAY;
        double SOMAZ;
        double AUX;
        double R3;
        double R5; //(  fim  )Dados do campo dipolar
        
        
        HEX = HEX + DELH;
        SXX = 0;
        SYY = 0;
        SZZ = 0;
        
        for(int iz=0; iz<NZ; iz++){
            for(int iy=0; iy<NY; iy++){
                for(int ix=0; ix<NX; ix++){
                    if(EXISTE[ix][iy] == 1){  //SY E SX VAI RECEBER ZERO SE NÃO EXISTIR
                        SX[ix][iy][iz] = SX0[ix][iy][iz]+DELTETA;
                        SY[ix][iy][iz] = SY0[ix][iy][iz]-DELTETA; //tenho dúvidas sobre o sinal aqui
                        SZ[ix][iy][iz] = SZ0[ix][iy][iz]-DELTETA; //tenho dúvidas sobre o sinal aqui também
                    } else{
                        SX[ix][iy][iz] = 0;
                        SY[ix][iy][iz] = 0;
                        SZ[ix][iy][iz] = 0;
                    }
                }
            }
        }
        
        HEXX = HEX * Math.cos(TETAH)*Math.sin(PHIH);
        HEXY = HEX * Math.sin(TETAH)*Math.sin(PHIH);
        HEXZ = HEX * Math.cos(PHIH);
        
        int ICONT = 0;
        
        do{
            ICONT = ICONT + 1;
            
            for(int iz=0; iz<NZ; iz++){
                for(int iy=0; iy<NY; iy++){
                    for(int ix=0; ix<NX; ix++){
                        SX0[ix][iy][iz] = SX[ix][iy][iz];
                        SY0[ix][iy][iz] = SY[ix][iy][iz];
                        SZ0[ix][iy][iz] = SZ[ix][iy][iz];
                    }
                }
            }
            
            double S1,S2,S3,S4,S5,S6;
            for(int iz=0; iz<NZ; iz++){
                for(int iy=0; iy<NY; iy++){
                    for(int ix=0; ix<NX; ix++){
                        if(EXISTE[ix][iy] == 1){
                            //CONDIÇÕES PARA EVITAR PROBLEMAS NO CALCULO
                            if(ix == 0){     S1 = 0;  }else{  S1 = SX[ix-1][iy][iz]; }
                            if(ix == NX-1){  S2 = 0;  }else{  S2 = SX[ix+1][iy][iz]; }
                            if(iy == 0){     S3 = 0;  }else{  S3 = SX[ix][iy-1][iz]; }
                            if(iy == NY-1){  S4 = 0;  }else{  S4 = SX[ix][iy+1][iz]; }
                            if(iz == 0){     S5 = 0;  }else{  S5 = SX[ix][iy][iz-1]; }
                            if(iz == NZ-1){  S6 = 0;  }else{  S6 = SX[ix][iy][iz+1]; }

                            //1/4 é a separação da energia para 4 vizinhos. em 3D será 1/6.

                            HX = EXCH[iz]*(S1 + S2 + S3 + S4) + EXCHM[iz]*S5 + EXCHP[iz]*S6 + HEXX + ANISX*SX[ix][iy][iz];

                            if(ix == 0){     S1 = 0;  }else{  S1 = SY[ix-1][iy][iz]; }
                            if(ix == NX-1){  S2 = 0;  }else{  S2 = SY[ix+1][iy][iz]; }
                            if(iy == 0){     S3 = 0;  }else{  S3 = SY[ix][iy-1][iz]; }
                            if(iy == NY-1){  S4 = 0;  }else{  S4 = SY[ix][iy+1][iz]; }
                            if(iz == 0){     S5 = 0;  }else{  S5 = SY[ix][iy][iz-1]; }
                            if(iz == NZ-1){  S6 = 0;  }else{  S6 = SY[ix][iy][iz+1]; }

                            HY = EXCH[iz]*(S1 + S2 + S3 + S4) + EXCHM[iz]*S5 + EXCHP[iz]*S6 + HEXY;

                            if(ix == 0){     S1 = 0;  }else{  S1 = SZ[ix-1][iy][iz]; }
                            if(ix == NX-1){  S2 = 0;  }else{  S2 = SZ[ix+1][iy][iz]; }
                            if(iy == 0){     S3 = 0;  }else{  S3 = SZ[ix][iy-1][iz]; }
                            if(iy == NY-1){  S4 = 0;  }else{  S4 = SZ[ix][iy+1][iz]; }
                            if(iz == 0){     S5 = 0;  }else{  S5 = SZ[ix][iy][iz-1]; }
                            if(iz == NZ-1){  S6 = 0;  }else{  S6 = SZ[ix][iy][iz+1]; }

                            HZ = EXCH[iz]*(S1 + S2 + S3 + S4) + EXCHM[iz]*S5 + EXCHP[iz]*S6 + HEXZ;

                            AK = 0;
                            AJ = 0;
                            AI = 0;
                            SOMAX = 0;
                            SOMAY = 0;
                            SOMAZ = 0;
                            R3 = 0;
                            R5 = 0;

                            for (int k = 0; k < NZ; k++) {
                                for (int j = 0; j < NY; j++) {
                                    for (int i = 0; i < NX; i++) {
                                        AUX = Math.sqrt(Math.pow(Math.abs(ix-i),2) + Math.pow(Math.abs(iy-j), 2) + Math.pow(Math.abs(iz-k), 2));

                                        if(AUX != 0){
                                            R3 = 1/Math.pow(AUX, 3);
                                            R5 = 1/Math.pow(AUX, 5);
                                        } else {
                                            R3 = 0;
                                            R5 = 0;
                                        }

                                        SOMAX = 3*(SX[i][j][k]*(ix-i) + SY[i][j][k]*(iy-j))*(ix-i)*R5 - SX[i][j][k]*R3;
                                        SOMAY = 3*(SX[i][j][k]*(ix-i) + SY[i][j][k]*(iy-j))*(iy-j)*R5 - SY[i][j][k]*R3;
                                        SOMAZ = 3*(SX[i][j][k]*(ix-i) + SY[i][j][k]*(iy-j))*(iz-i)*R5 - SZ[i][j][k]*R3;

                                        AI = AI + SOMAX*ALFAD[k];
                                        AJ = AJ + SOMAY*ALFAD[k];
                                        AK = AK + SOMAY*ALFAD[k];
                                    }
                                }
                            }
                            HX = HX + AI; //LOCAL FIELD AT EACH SPIN
                            HY = HY + AJ;
                            HZ = HZ + AK;

                            HP = Math.sqrt(HX*HX + HY*HY + HZ*HZ);

                            SX[ix][iy][iz] = HX/HP;
                            SY[ix][iy][iz] = HY/HP;
                            SZ[ix][iy][iz] = HZ/HP;
                            HTX[ix][iy][iz] = HX;
                            HTY[ix][iy][iz] = HY;
                            HTZ[ix][iy][iz] = HZ;
                        } else {
                            HTX[ix][iy][iz] = 0;
                            HTY[ix][iy][iz] = 0;
                            HTZ[ix][iy][iz] = 0;
                        }
                    }
                }
            }
            
            for(int iz=0; iz<NZ; iz++){
                for(int iy=0; iy<NY; iy++){
                    for(int ix=0; ix<NX; ix++){
                        if(EXISTE[ix][iy] == 1){
                            TALX =Math.abs(SY0[ix][iy][iz]*HTZ[ix][iy][iz] - SZ0[ix][iy][iz]*HTY[ix][iy][iz]);
                            TALY =Math.abs(SZ0[ix][iy][iz]*HTX[ix][iy][iz] - SX0[ix][iy][iz]*HTZ[ix][iy][iz]);
                            TALZ =Math.abs(SX0[ix][iy][iz]*HTY[ix][iy][iz] - SY0[ix][iy][iz]*HTX[ix][iy][iz]);

                            if(TALX > TOLTETA){
                                break; //sai deste FOR imediatamente
                            }
                            if(TALY > TOLTETA){
                                break; //sai deste FOR imediatamente
                            }
                            if(TALZ > TOLTETA){
                                break; //sai deste FOR imediatamente
                            }
                        }
                    }
                    
                    if(TALX > TOLTETA){
                        break; //sai deste FOR imediatamente
                    }
                    if(TALY > TOLTETA){
                        break; //sai deste FOR imediatamente
                    }
                    if(TALZ > TOLTETA){
                        break; //sai deste outro FOR imediatamente
                    }
                }
                
                if(TALX > TOLTETA){
                    break; //sai deste FOR imediatamente
                }
                if(TALY > TOLTETA){
                    break; //sai deste FOR imediatamente
                }
                if(TALZ > TOLTETA){
                        break; //sai deste outro FOR também
                }
            }
        }while((ICONT <= getNCHECK()) && ((TALX>TOLTETA)||(TALY>TOLTETA)||(TALZ>TOLTETA)));//FIM DO LOOP DO CAMPO
        
        double SP;
        for(int iz=0; iz<NZ; iz++){
            for(int iy=0; iy<NY; iy++){
                for(int ix=0; ix<NX; ix++){
                    if(EXISTE[ix][iy] == 1){
                        SXX = SXX + SX[ix][iy][iz];
                        SYY = SYY + SY[ix][iy][iz];
                        SZZ = SZZ + SZ[ix][iy][iz];
                        //ANGULO DE CADA CAMPO (RAD)
                        TETA[ix][iy][iz] = Math.atan2(SY[ix][iy][iz], SX[ix][iy][iz]);
                        SP = Math.sqrt(Math.pow(SX[ix][iy][iz],2)+Math.pow(SY[ix][iy][iz],2));
                        PHI[ix][iy][iz]=Math.atan2(SP,SZ[ix][iy][iz]);
                    }
                }
            }
        }
        
        SXX = SXX/NTOT;
        SYY = SYY/NTOT;
        SZZ = SZZ/NTOT;
        
        //GRAVANDO RESULTADOS RELEVANTES
        res.setSX(SX);
        res.setSY(SY);
        res.setSY(SZ);
        res.setSXX(SXX);
        res.setSYY(SYY);
        res.setSZZ(SZZ);
        res.setTETA(TETA);
        res.setPHI(PHI);
        
        //DADOS DO GRAFICO
        DADOS_GRAFICO += HEX+" "+SXX+" "+SYY+" "+SZZ+"\r\n"; //LEMBRAR DE MUDAR ESSE \r LOGO
        //--------------------------------------------------------
        
        texto = "_______________________________________________\n";
        texto += "IH: "+ih+"/"+getNH()+"; HEX= "+HEX+";\nSXX="+SXX+"; SYY="+SYY+"; SZZ="+SZZ+";\nTESTES: "+ICONT+"/"+getNCHECK()+"\n\n";
        /**
        if(ih == getNH()){ //Isso faz esta parte abaixo ser mostrada somente para o ultimo IH
            for (int i=0; i<NX;i++){
                for (int j=0; j<NY;j++){
                    texto += "Posicao: "+(i+1)+","+(j+1)+"; ";
                    texto += "\nSX= "+SX[i][j]+"; ";
                    texto += "SY= "+SY[i][j]+"; ";
                    texto += "\nTeta= "+TETA[i][j]*180/Math.PI+"°\n\n"; // MOSTRANDO EM GRAUS

                }
            }
        }
        */
        
        res.setDADOS_GRAFICO(DADOS_GRAFICO);
        
        texto = rede.Area().getText()+texto;
        rede.Area().setText(texto);
    }
    
    public void PontosElipse(int X, int Y){ // diametros da elipse
        EXISTE = new int[X][Y]; //talvez seja melhor fazer isso só quando a configuração eliptica for escolhida
        double a = (double) (X-1)/2; //RAIO EM X
        double b = (double) (Y-1)/2; //RAIO EM Y
        
        double y1;
        double y2;
        
        for(int xo = 0; xo < NX; xo++){
            for(int yo = 0; yo < NY; yo++){
                
                y1 = b + (b+0.2)*Math.sqrt(1 - (xo-a)*(xo-a)/((a+0.2)*(a+0.2)));
                y2 = b - (b+0.2)*Math.sqrt(1 - (xo-a)*(xo-a)/((a+0.2)*(a+0.2)));
                
                if((yo >= y2)&&(yo <= y1)){
                    EXISTE[xo][yo] = 1;
                } else {
                    EXISTE[xo][yo] = 0;
                }
            }
        }
        
    }
}
