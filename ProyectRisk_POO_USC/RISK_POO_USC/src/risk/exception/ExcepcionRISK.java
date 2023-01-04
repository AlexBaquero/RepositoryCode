package risk.exception;

public abstract class ExcepcionRISK extends Exception{
    private int codigo; 
    
    protected ExcepcionRISK(int codigo,String descripcion){
        super(descripcion);
        this.codigo = codigo;
    }
    
    //Devulve la descripcion de la excepción
    public String getMensajeError(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\033[31m{\n\033[31mcodigo de error: ")
                .append(codigo)
                .append("\n\033[31mdescripcion: ")
                .append(this.getMessage())
                .append("\n}\n\033[40;30m");
        return stringBuilder.toString();
    }
}
