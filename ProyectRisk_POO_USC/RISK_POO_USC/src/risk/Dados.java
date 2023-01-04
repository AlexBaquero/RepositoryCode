package risk;

public class Dados {

    private Integer dadoAtaque1;
    private Integer dadoAtaque2;
    private Integer dadoAtaque3;
    private Integer dadoDefensa1;
    private Integer dadoDefensa2;

    public Dados() {

    }

    //lanzamos los 3 dados de ataque los cuales seran un numero aleatorio del 1 al 6
    public void lanzarDadosAtaque() {
        dadoAtaque1 = (int) Math.floor(Math.random() * 6) + 1;
        dadoAtaque2 = (int) Math.floor(Math.random() * 6) + 1;
        dadoAtaque3 = (int) Math.floor(Math.random() * 6) + 1;

    }

    //lanzamos los 2 dados de defensa los cuales seran un numero aleatorio del 1 al 6
    public void lanzarDadosDefensa() {
        dadoDefensa1 = (int) Math.floor(Math.random() * 6) + 1;
        dadoDefensa2 = (int) Math.floor(Math.random() * 6) + 1;

    }

    //devolvemos el primer dado de ataque 
    public Integer getDadoAtaque1() {
        return dadoAtaque1;
    }
    
    //devolvemos el segundo dado de ataque
    public Integer getDadoAtaque2() {
        return dadoAtaque2;
    }

    //devolvemos el tercer dado de ataque
    public Integer getDadoAtaque3() {
        return dadoAtaque3;
    }

    //devolvemos el primer dado de defensa
    public Integer getDadoDefensa1() {
        return dadoDefensa1;
    }

    //devolvemos el segundo dado de defensa
    public Integer getDadoDefensa2() {
        return dadoDefensa2;
    }

}