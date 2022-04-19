/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Execucao.Entrada;
import Execucao.Principal;

/**
 *
 * @author Rodrigo
 */
public class CampoMagnetico {
    
    //static RedeParticulas rp = new RedeParticulas();
    //static int NCELLX = rp.getX();
    //static int NCELLY = rp.getY();
    //static int NCELLZ = rp.getZ();
    
    int TamPanelX;
    int TamPanelY;
    int TamPanelZ;
    
    String       FM;
    String   ANISP1;
    int          NX;
    int          NY;
    int          NZ;
    double       HI;
    double       HF;
    int          NH;
    int      NCHECK;
    double  DELTETA;
    double  TOLTETA;
    double     PHIH;
    double    TETAH;
    double        D;
    double    EXCH1;
    double   ALFAD1;
    double    ALFA1;
    double    ANISX;
    double     DELH;
    double      HEX;
    double      SXX;
    double      SYY;
    double      SZZ;    
    double     NTOT;
    double []  EXCH;
    double [] EXCHP;
    double [] EXCHM;
    double [] ALFAD;
    
    
    double     HEXX;
    double     HEXY;
    double     HEXZ;
    double       HX;
    double       HY;
    double       HZ;
    double       HP;
    double     TALX;
    double     TALY;
    double     TALZ;
    
    String DADOS_GRAFICO;
    String DADOS_DIPOLAR;
    String texto;
    
    double [][][]      SX;
    double [][][]      SY;
    double [][][]      SZ;
    double [][][]     SX0;
    double [][][]     SY0;
    double [][][]     SZ0;
    double [][][]    TETA;
    double [][][]     PHI;
    double [][][]     HDD;
    double [][][]    HDDX;
    double [][][]    HDDY;
    double [][][]    HDDZ;
    double [][][] TETAHDD;
    
    double [][][] HTX;
    double [][][] HTY;
    double [][][] HTZ;
    
    int [][] EXISTE; //PARA OS PONTOS DA ELIPSE NA MATRIZ
    int tipoGeometria;
    
    public CampoMagnetico(){
        
    }
    
    public CampoMagnetico(int X, int Y, int Z){
        EXCH  = new double[Z];
        EXCHP = new double[Z];
        EXCHM = new double[Z];
        ALFAD = new double[Z];
        
        SX      = new double[X][Y][Z];
        SY      = new double[X][Y][Z];
        SZ      = new double[X][Y][Z];
        SX0     = new double[X][Y][Z];
        SY0     = new double[X][Y][Z];
        SZ0     = new double[X][Y][Z];
        TETA    = new double[X][Y][Z];
        PHI     = new double[X][Y][Z];
        HDD     = new double[X][Y][Z];
        HDDX    = new double[X][Y][Z];
        HDDY    = new double[X][Y][Z];
        HDDZ    = new double[X][Y][Z];
        TETAHDD = new double[X][Y][Z];
        
        HTX = new double[X][Y][Z];
        HTY = new double[X][Y][Z];
        HTZ = new double[X][Y][Z];
        
        //EXISTE = new int[X][Y]; //talvez seja melhor fazer isso só quando a configuração eliptica for escolhida
        
        NX      = X;
        NY      = Y;
        NZ      = Z;
    }
    
    
    public String getDADOS_DIPOLAR(){
        return DADOS_DIPOLAR;
    }

    public String getDADOS_GRAFICO(){
        return DADOS_GRAFICO;
    }
    
    public String getFM() {
        return FM;
    }
    
    public void setFM(String aFM) {
        FM = aFM;
    }
    
    public int getNX() {
        return NX;
    }
    
    public void setNX(int aNX) {
        NX = aNX;
    }
    
    public int getNY() {
        return NY;
    }
    
    public void setNY(int aNY) {
        NY = aNY;
    }
    
    public int getNZ() {
        return NZ;
    }
    
    public void setNZ(int aNZ) {
        NZ = aNZ;
    }
    
    public int getNH() {
        return NH;
    }
    
    public void setNH(int aNH) {
        NH = aNH;
    }
    
    public double[][][] getSX() {
        return SX;
    }
    
    public void setSX(double[][][] aSX) {
        SX = aSX;
    }
    
    public double[][][] getSY() {
        return SY;
    }
    
    public void setSY(double[][][] aSY) {
        SY = aSY;
    }
    
    //**********************************************
    //TEMPORÁRIO
    public double[][] getSX2() {
        double SX2[][] = new double[NX][NY];
        
        for (int i=0; i<NX; i++){
            for (int j=0; j<NY; j++){
                SX2[i][j] = SX[i][j][0];
            }
        }
        return SX2;
    }
    
    public double[][] getSY2() {
        double SY2[][] = new double[NX][NY];
        
        for (int i=0; i<NX; i++){
            for (int j=0; j<NY; j++){
                SY2[i][j] = SY[i][j][0];
            }
        }
        return SY2;
    }
    //************************************************
    
    public double getHI() {
        return HI;
    }
    
    public void setHI(double aHI) {
        HI = aHI;
    }
    
    public double getHF() {
        return HF;
    }
    
    public void setHF(double aHF) {
        HF = aHF;
    }
    
    public int getNCHECK() {
        return NCHECK;
    }
    
    public void setNCHECK(int aNCHECK) {
        NCHECK = aNCHECK;
    }
    
    public int getTamPanelX() {
        return TamPanelX;
    }
    
    public void setTamPanelX(int aTamPanelX) {
        TamPanelX = aTamPanelX;
    }
    
    public int getTamPanelY() {
        return TamPanelY;
    }
    
    public void setTamPanelY(int aTamPanelY) {
        TamPanelY = aTamPanelY;
    }
    
    public String getANISP1() {
        return ANISP1;
    }
    
    public void setANISP1(String aANISP1) {
        ANISP1 = aANISP1;
    }
    
    public int getEXISTE(int i, int j) {
        return EXISTE[i][j];
    }
    
    public int getTipoGeometria() {
        return tipoGeometria;
    }

    public void setTipoGeometria(int aTipoGeometria) {
        tipoGeometria = aTipoGeometria;
    }

    public double getD() {
        return D;
    }

    public void setD(double aD) {
        D = aD;
    }
}