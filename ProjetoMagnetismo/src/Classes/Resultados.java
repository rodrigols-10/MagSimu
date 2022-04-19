/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author rodri
 */
public class Resultados {
    //apagar tudo que for inutil
    
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
    
    private String DADOS_GRAFICO;
    private String DADOS_DIPOLAR;
    private String texto;
    
    double [][][]      SX;
    double [][][]      SY;
    double [][][]      SZ;
    double [][][]    TETA;
    private double [][][]     PHI;
    double [][][]     HDD;
    double [][][]    HDDX;
    double [][][]    HDDY;
    double [][][]    HDDZ;
    double [][][] TETAHDD;
    int tipoGeometria;
    int [][] EXISTE; //PARA OS PONTOS DA ELIPSE NA MATRIZ
    
    public Resultados(){
        
    }
    
    public Resultados(int X, int Y, int Z){
        EXCH  = new double[Z];
        EXCHP = new double[Z];
        EXCHM = new double[Z];
        ALFAD = new double[Z];
        
        SX      = new double[X][Y][Z];
        SY      = new double[X][Y][Z];
        SZ      = new double[X][Y][Z];
        TETA    = new double[X][Y][Z];
        PHI     = new double[X][Y][Z];
        HDD     = new double[X][Y][Z];
        HDDX    = new double[X][Y][Z];
        HDDY    = new double[X][Y][Z];
        HDDZ    = new double[X][Y][Z];
        TETAHDD = new double[X][Y][Z];
        
        NX      = X;
        NY      = Y;
        NZ      = Z;
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
    }
    
    public String getDADOS_DIPOLAR(){
        return DADOS_DIPOLAR;
    }

    public String getDADOS_GRAFICO(){
        return DADOS_GRAFICO;
    }
    
    public void setDADOS_GRAFICO(String DADOS_GRAFICO) {
        this.DADOS_GRAFICO = DADOS_GRAFICO;
    }

    public void setDADOS_DIPOLAR(String DADOS_DIPOLAR) {
        this.DADOS_DIPOLAR = DADOS_DIPOLAR;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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
    
    public double getSXX() {
        return SXX;
    }
    
    public void setSXX(double SXX) {
        this.SXX = SXX;
    }
    
    public double getSYY() {
        return SYY;
    }
    
    public void setSYY(double SYY) {
        this.SYY = SYY;
    }
    
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
    
    public String getANISP1() {
        return ANISP1;
    }
    
    public void setANISP1(String aANISP1) {
        ANISP1 = aANISP1;
    }
    
    public int getEXISTE(int i, int j) { //Usado por JRede
        return EXISTE[i][j];
    }
    
    public int [][] getEXISTE() {
        return EXISTE;
    }
    
    public void setEXISTE(int[][] aEXISTE){
        EXISTE = aEXISTE;
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

    /**
     * @return the TETAHDD
     */
    public double[][][] getTETAHDD() {
        return TETAHDD;
    }

    /**
     * @param TETAHDD the TETAHDD to set
     */
    public void setTETAHDD(double[][][] TETAHDD) {
        this.TETAHDD = TETAHDD;
    }
    
    public double[][][] getTETA() {
        return TETA;
    }
    
    public void setTETA(double[][][] TETA) {
        this.TETA = TETA;
    }
    
    public double[][][] getHDD() {
        return HDD;
    }
    
    public void setHDD(double[][][] HDD) {
        this.HDD = HDD;
    }
    
    public double[][][] getHDDX() {
        return HDDX;
    }
    
    public void setHDDX(double[][][] HDDX) {
        this.HDDX = HDDX;
    }
    
    public double[][][] getHDDY() {
        return HDDY;
    }
    
    public void setHDDY(double[][][] HDDY) {
        this.HDDY = HDDY;
    }
    
    public double[][][] getPHI() {
        return PHI;
    }

    public void setPHI(double[][][] PHI) {
        this.PHI = PHI;
    }
    
    public double getSZZ() {
        return SZZ;
    }
    
    public void setSZZ(double SZZ) {
        this.SZZ = SZZ;
    }
}
