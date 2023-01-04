package risk;

import java.util.HashMap;

public class ListadoCartasMision {
    private static final HashMap<String,CartaMision> CartasMisionHash = new HashMap<String,CartaMision>();

    static{
        
        //vamos creando las cartas y luego las añadimos a un hashmap
        CartaMision M1 = new CartaMision("M1","Conquistar 24 países de la preferencia del jugador");
        CartaMision M2 = new CartaMision("M2","Conquistar 18 países de la preferencia del jugador con un mínimo de dos ejércitos");
        CartaMision M31 = new CartaMision("M31","Conquistar Asia y América del Sur");
        CartaMision M32 = new CartaMision("M32","Conquistar Asia y África");
        CartaMision M33 = new CartaMision("M33","Conquistar América del Norte y África");
        CartaMision M34 = new CartaMision("M34","Conquistar América del Norte y Oceanía");
        CartaMision M41 = new CartaMision("M41","Destruir el ejército AMARILLO");
        CartaMision M42 = new CartaMision("M42","Destruir el ejército AZUL");
        CartaMision M43 = new CartaMision("M43","Destruir el ejército CYAN");
        CartaMision M44 = new CartaMision("M44","Destruir el ejército ROJO");
        CartaMision M45 = new CartaMision("M45","Destruir el ejército VERDE");
        CartaMision M46 = new CartaMision("M46","Destruir el ejército VIOLETA");
        
        CartasMisionHash.put("M1", M1);
        CartasMisionHash.put("M2", M2);
        CartasMisionHash.put("M31", M31);
        CartasMisionHash.put("M32", M32);
        CartasMisionHash.put("M33", M33);
        CartasMisionHash.put("M34", M34);
        CartasMisionHash.put("M41", M41);
        CartasMisionHash.put("M42", M42);
        CartasMisionHash.put("M43", M43);
        CartasMisionHash.put("M44", M44);
        CartasMisionHash.put("M45", M45);
        CartasMisionHash.put("M46", M46);
    }
    
    //devuelve la cartamision correspondiente al codigo introducido
    public static CartaMision getCartaMision(String cod){
        return CartasMisionHash.get(cod);
    }
    
}