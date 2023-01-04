
package aplicacion;

//Clase de usuarios
public class Usuario {
    private String id_usuario;
    private String contrasena;
    private Integer fondos;
    private TipoUsuario tipoUsuario;
    private int enEspera; //1 En Espera, 0 autorizado, -1 ha solicitado baja

    public Usuario(String id_usuario, String contr, int fondos, String tipo) {
        this.id_usuario = id_usuario;
        this.contrasena = contr;
        this.fondos = fondos;
        this.tipoUsuario =  TipoUsuario.valueOf(tipo);
        this.enEspera = 1;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public int getEnEspera() {
        return enEspera;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Integer getFondos() {
        return fondos;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setFondos(int fondos) {
        this.fondos = fondos;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setEnEspera(int enEspera) {
        this.enEspera = enEspera;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id_usuario=" + id_usuario + ", contrasena=" + contrasena + ", fondos=" + fondos + ", tipoUsuario=" + tipoUsuario + ", enEspera=" + enEspera + '}';
    }
    
    
}