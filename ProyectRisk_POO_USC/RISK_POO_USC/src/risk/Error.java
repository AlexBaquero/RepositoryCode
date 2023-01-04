package risk;

public class Error {
    private String descripcion;
    private int codigo;
    
    public Error(int codigo,String descripcion){
        this.codigo=codigo;
        this.descripcion=descripcion;
    }
    
    //devuelve el codigo 
    public int getCodigoError(){
        return codigo;
    }
    
    //devuelve la descripcion del error
    public String getDescripcionError(){
        return descripcion;
    }
    
  
    @Override
    public String toString(){
        String color = "\033[41;30m";
        String s = "{";
        String s2 = "}";
        int cod = codigo;
        String des = this.descripcion;
        String restart = "\033[40;30m";
        
        return color+s+"\n"+"codigo de error: "+cod+"\n"+"descripcion: "+des+"\n"+s2+restart;
    } 
}