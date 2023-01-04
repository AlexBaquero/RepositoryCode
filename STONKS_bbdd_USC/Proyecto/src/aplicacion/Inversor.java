package aplicacion;

//Clase de inversores
public class Inversor {
    private Usuario id_usuario;
    private String DNI;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String calle;
    private Integer portal;
    private Integer piso;
    private String puerta;
    private Integer telefono;

    public Inversor(Usuario id_usuario, String DNI, String nombre, String apellido1, String apellido2, String calle, Integer portal, Integer piso, String puerta, Integer telefono) {
        this.id_usuario = id_usuario;
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.calle = calle;
        this.portal = portal;
        this.piso = piso;
        this.puerta = puerta;
        this.telefono = telefono;
    }

    public Usuario getId_usuario() {
        return id_usuario;
    }

    public String getDNI() {
        return DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getCalle() {
        return calle;
    }

    public Integer getPortal() {
        return portal;
    }

    public Integer getPiso() {
        return piso;
    }

    public String getPuerta() {
        return puerta;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setPortal(Integer portal) {
        this.portal = portal;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Inversor{" + "id_usuario=" + id_usuario + ", DNI=" + DNI + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", calle=" + calle + ", portal=" + portal + ", piso=" + piso + ", puerta=" + puerta + ", telefono=" + telefono + '}';
    }
    
}
