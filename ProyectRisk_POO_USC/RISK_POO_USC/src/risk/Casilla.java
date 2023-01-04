package risk;

public class Casilla {
    private int x;
    private int y;
    
    private Pais pais;
    private tipoFrontera frontera;
    
    
    public static enum tipoFrontera {
        VERTICAL,
        HORIZONTAL
    };
    

    private Casilla(int x,int y){
        this.x = x;
        this.y= y;
    }

    //crea una casilla tipo oceano y la devuelve 
    public static Casilla casillaOceano(int x, int y) {
        return new Casilla(x, y);
    }
    
     //crea una casilla tipo pais y la devuelve 
    public static Casilla casillaPais(Pais pais, int x, int y) {
        Casilla casilla = new Casilla(x, y);
        casilla.pais = pais;
        return casilla;
    }
    
     //crea una casilla tipo frontera y la devuelve 
    public static Casilla casillaFrontera(int x, int y,tipoFrontera frontera) {
        Casilla casilla = new Casilla(x, y);
        casilla.frontera = frontera;
        return casilla;
    }

    //devuelve la posicoion x
    public int getX(){
        return x;
    }
    //devuelve la posicoon y
    public int getY(){
        return y;
    }

    //devuelve el pais de la casilla
    public Pais getPais() {
        return pais;
    }
    
    
    //devuelve el tipo de frontera 
    public tipoFrontera getFrontera() {
        return frontera;
    }
    
    //comprueba que la casilla es un pais
    public boolean esPais(){
        if(pais!=null){
            return true;
        }
        return false;
    }
    
    //devuelve el codigo del color
    public String color(){
        if(pais!=null){
            return pais.getJugador().getCodigoColor();
        }
        else{
            return "";
        }
    }
    
    //devuelve el número de ejercitos que hay en la casilla
    public Integer ejercitosCasilla(){
        return pais.getNumeroEjercitos();
    }
    
    @Override
    public boolean equals (Object object){
        if(object instanceof Casilla){
            Casilla casilla = (Casilla) object;
            return this.x==casilla.getX() && this.y==casilla.getY();
        }
        return false;
    }
    
    @Override
    public String toString() {
        if (pais != null) {
            String restart = "\033[40;30m";
            return pais.getColor()+pais.getAbreviaturaP()+restart;
        } else if (frontera != null) {
            if (frontera == tipoFrontera.HORIZONTAL){
                return "\033[31m-----------\033[40;30m";
            } else {
                return "\033[31m     |     \033[40;30m";
            }
        }
        
        return "           ";
    }

}